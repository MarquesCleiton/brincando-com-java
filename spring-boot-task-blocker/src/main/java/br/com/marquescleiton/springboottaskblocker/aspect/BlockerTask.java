package br.com.marquescleiton.springboottaskblocker.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BlockerTask {
	String nomeTask() default "";
	String bloquearNoMaximo() default "PT10S";
}
