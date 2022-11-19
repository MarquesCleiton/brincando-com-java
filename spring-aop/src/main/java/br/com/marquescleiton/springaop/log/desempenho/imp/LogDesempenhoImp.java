package br.com.marquescleiton.springaop.log.desempenho.imp;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import br.com.marquescleiton.springaop.log.desempenho.annotation.LogDesempenho;
import br.com.marquescleiton.springaop.log.desempenho.config.LogDesempenhoAspectConfig;
import br.com.marquescleiton.springaop.log.desempenho.exception.LogDesempenhoException;
import br.com.marquescleiton.springaop.log.desempenho.service.LogDesempenhoService;

@Service
public class LogDesempenhoImp implements LogDesempenhoService {

	public void logarExecucao(String descricao, Runnable run) {
		try {
			LogDesempenhoService logService = criaLogDesempenhoService();
			logService.runRunnable(descricao, run);
		} catch (Exception e) {
			System.out
					.println("Não foi possível criar log do metodo. Executando metodo normalmente... " + e.getMessage());
			run.run();
		}
	}
	
	@Override
	@LogDesempenho
	public void runRunnable(String descricao, Runnable run) {
		run.run();
	}

	private LogDesempenhoService criaLogDesempenhoService() throws LogDesempenhoException {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(LogDesempenhoAspectConfig.class);
			context.refresh();
			return context.getBean(LogDesempenhoService.class);
		} catch (BeansException | IllegalStateException e) {
			throw new LogDesempenhoException(e, "Não foi possível criar o service");
		}
	}
}
