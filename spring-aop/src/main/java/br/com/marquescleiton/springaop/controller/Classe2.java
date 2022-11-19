package br.com.marquescleiton.springaop.controller;

import java.time.LocalDateTime;

public class Classe2 {

	static String getDayWeek(){

		return LocalDateTime.now().plusDays(1).getDayOfWeek().toString();
	}
}
