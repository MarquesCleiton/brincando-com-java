package br.com.marquescleiton.springboottaskblocker.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.marquescleiton.springboottaskblocker.aspect.BlockerTask;
import br.com.marquescleiton.springboottaskblocker.blocker.Blocker;
import br.com.marquescleiton.springboottaskblocker.jpa.ShedLockRepo;

@Component
public class Scheduler {
	
	@Autowired
	private Blocker blocker = new Blocker();
	
	@Autowired
	ShedLockRepo shedLockRepo;
	
	@Scheduled(cron = "0/5 * * ? * *")
	@BlockerTask(nomeTask = "coisa", bloquearNoMaximo = "PT1M")
	public void minhaTask() {
		System.out.println("Teste");
		blocker.unBlock("2");
		//blocker.unBlock("Minha tarefa");
	}
}
