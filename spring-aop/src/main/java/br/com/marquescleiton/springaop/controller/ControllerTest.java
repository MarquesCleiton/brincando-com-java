package br.com.marquescleiton.springaop.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marquescleiton.springaop.log.desempenho.imp.LogDesempenhoImp;

@RestController("/")
public class ControllerTest{

	LogDesempenhoImp logDesempenhoImp = new LogDesempenhoImp();
	
	@GetMapping("/teste")
	public String testandoAspect() {
		String msg = "Oi";
		//logDesempenhoImp.logarExecucao("Testando log", () -> simulaProcessamento(5));
		//logRunnable(() -> simulaProcessamento(5));
		
		return msg;
	}

	
	private void logRunnable(Runnable run) {
		LocalDateTime ini = LocalDateTime.now();
		run.run();
		LocalDateTime fim = LocalDateTime.now();
		Duration dur = Duration.between(ini, fim);
		System.out.println("TempoProcessamento: " + dur.toMillis() + "ms");
		
	}
	

	
	private boolean isPar(int num) {
		return num%2 == 0;
	}
	
	public void simulaProcessamento(int segundos) {
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
