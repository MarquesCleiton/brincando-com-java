package br.com.marquescleiton.springaop.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MeuTeste {

	public static void main(String[] args) throws Exception{
		// boolean a = (boolean) rerunRunnable(() -> simulaProcessamento(5));
		Classe2 c2 = new Classe2();
		
		String b = (String) logReturnableTask("getDayWeek", () -> getDayWeek());
		
//		Runnable a = () -> {
//			try {
//				simulaProcessamento(10);
//				throw new Exception ();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				
//			}
//		};
		
		logVoidTask("", () -> simulaProcessamento(10));
//		simulaProcessamento(5);
		String c = Classe2.getDayWeek();

		// System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	
	private static void teste (MyRunnable run) throws Exception {
		run.run();
	}

	private static Object logReturnableTask(String descricao, Callable<?> returnableTask) throws Exception {
		
		return log(descricao, returnableTask, null);
	}
	
	private static void logVoidTask(String descricao, MyRunnable voidTask) throws Exception {
		log(descricao, null, voidTask);
	}

	private static Object log(String descricao, Callable<?> returnableTask, Runnable voidTask) throws Exception {

		ExecutorService exe = Executors.newSingleThreadExecutor();
		Future<?> future;
		Object obj = null;
		
		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fim;

		if (returnableTask == null) {
			returnableTask = Executors.callable(voidTask);
		}
		
		future = exe.submit(returnableTask);
		
		exe.shutdown();
		
		try {
			obj = future.get();
			fim = LocalDateTime.now();
			Duration d = Duration.between(inicio, fim);
			System.out.println("-------------------------------	"			);
			System.out.println("Processo: " + descricao 		 			);
			System.out.println("Deu ruim: não"					 			);
			System.out.println("TempoProcessament: " + d.toMillis() + "ms" 	);
			System.out.println("--------------------------------"			);
			return obj;
		} catch (InterruptedException | ExecutionException e) {
			fim = LocalDateTime.now();
			Duration d = Duration.between(inicio, fim);
			System.out.println("--------------------------------");
			System.out.println("Processo: " + descricao);
			System.out.println("Deu ruim: " + e.getMessage());
			System.out.println("TempoProcessament: " + d.toMillis() + "ms" );
			System.out.println("--------------------------------");
			throw new Exception("Deu ruim tio", e);
		}
	}


	private static boolean isPar(int num) {
		return num % 2 == 0;
	}

	private static String getDayWeek() throws Exception {

		return LocalDateTime.now().getDayOfWeek().toString();
	}

	public static void simulaProcessamento(int segundos) throws Exception{
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
		//throw new Exception("Deu ruim bixo");
	}

}
