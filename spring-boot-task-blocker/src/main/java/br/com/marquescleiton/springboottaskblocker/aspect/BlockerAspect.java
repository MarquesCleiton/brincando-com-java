package br.com.marquescleiton.springboottaskblocker.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.marquescleiton.springboottaskblocker.blocker.Blocker;

@Aspect
@Component
public class BlockerAspect {
	@Autowired
	private Blocker blocker = new Blocker();
	
	@Around("@annotation(BlockerTask)")
	public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable{
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		
		BlockerTask myAnnotation = method.getAnnotation(BlockerTask.class);		
		
		if (blocker.block(myAnnotation.nomeTask(), myAnnotation.bloquearNoMaximo()).isBlocked()) {
			return point.proceed();
		}
		
		return null;
	}
}
