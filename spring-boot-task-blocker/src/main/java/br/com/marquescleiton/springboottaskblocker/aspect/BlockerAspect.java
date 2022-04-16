package br.com.marquescleiton.springboottaskblocker.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.marquescleiton.springboottaskblocker.blocker.Blocker;

@Aspect
@Component
public class BlockerAspect {

	@Autowired
	private Blocker blocker;

	@Before("@annotation(BlockerTask)")
	public void checkSomethingBefore(JoinPoint joinPoint) {
		System.out.println("Executando @Before");
	}

	@Around("@annotation(BlockerTask)")
	private Object aroundAdvice(ProceedingJoinPoint point) throws Throwable {
		System.out.println("Executando @Around");
		BlockerTask myAnnotation = myAnnotation(point);

		if (blocker.isBlocked(myAnnotation.nomeTask()) == false) {
			blocker.block(myAnnotation.nomeTask(), myAnnotation.bloquearNoMaximo());
			return point.proceed();
		}
		return null;
	}

	@AfterReturning(pointcut = "@annotation(BlockerTask)")
	public void checkSomethingAfter(JoinPoint joinPoint) {
		System.out.println("Executando @AfterReturning");
		
		BlockerTask myAnnotation = myAnnotation(joinPoint);

		if (myAnnotation.desbloquearNoFim() == true) {
			blocker.unBlock(myAnnotation.nomeTask());
		}
	}

	private BlockerTask myAnnotation(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		return method.getAnnotation(BlockerTask.class);
	}
}
