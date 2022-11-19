package br.com.marquescleiton.springaop.log.desempenho.imp.aspect;

import java.time.Duration;
import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogDesempenhoAspect {

	@Around("@annotation(br.com.marquescleiton.springaop.log.desempenho.annotation.LogDesempenho)")
	private Object intercept(ProceedingJoinPoint point) throws Throwable {
		
		String descricao = (String) point.getArgs() [0];
		
		System.out.println("Descricao: " + descricao);
		
		LocalDateTime ini = LocalDateTime.now();
		Object obj = point.proceed();
		LocalDateTime fim = LocalDateTime.now();
		
		Duration dur = Duration.between(ini, fim);
		
		System.out.println("Descrição: " + descricao);
		System.out.println("TempoProcessamento: " + dur.toMillis() + "ms");
		
		return obj;
	}
}
