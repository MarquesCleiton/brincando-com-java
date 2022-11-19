package br.com.marquescleiton.springaop.log.desempenho.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * 
 * @author Cleiton
 *	Fonte: https://medium.com/trabe/understanding-aop-in-spring-from-magic-to-proxies-6f5911e5e5a8
 */
@Configuration
@EnableAspectJAutoProxy 
@ComponentScan("br.com.marquescleiton.springaop.log.desempenho.imp")
public class LogDesempenhoAspectConfig {}
