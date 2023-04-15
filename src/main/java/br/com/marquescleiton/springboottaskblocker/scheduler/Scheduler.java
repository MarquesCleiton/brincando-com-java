package br.com.marquescleiton.springboottaskblocker.scheduler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.marquescleiton.springboottaskblocker.aspect.BlockerTask;
import br.com.marquescleiton.springboottaskblocker.blocker.Blocker;
import br.com.marquescleiton.springboottaskblocker.jpa.ShedLockRepo;

@Component
public class Scheduler {
	
	@Autowired
	private Blocker blocker;
	
	@Autowired
	ShedLockRepo shedLockRepo;
	
	@Scheduled(cron = "0/5 * * ? * *")
	@BlockerTask(nomeTask = "Minha task", bloquearNoMaximo = "PT1M", desbloquearNoFim = false)
	public void minhaTask() {
		System.out.println("\nIniciando tarefa");
		simulaProcessamento(30);
		
		//Caso deseje desbloquear Task antes de finalizar metodo
		blocker.unBlock("Minha task");
	}
	
	private void simulaProcessamento(int segundos) {
		System.out.println("Processamento de " + segundos + "s");
		LocalDateTime fim = LocalDateTime.now().plusSeconds(segundos);
		boolean mostra = true;

		while (LocalDateTime.now().isBefore(fim)) {

			if (LocalDateTime.now().getSecond() % 5 == 0) {
				if (mostra) {
					System.out.println(LocalDateTime.now());
				}
				mostra = false;
			} else {
				mostra = true;
			}
		}
		System.out.println("FIM Processamento de " + segundos + "s");
	}
}
