package br.com.marquescleiton.springboottaskblocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootTaskBlockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTaskBlockerApplication.class, args);
	}

}
