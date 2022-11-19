package br.com.marquescleiton.springaop.log.desempenho.exception;

public class LogDesempenhoException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public LogDesempenhoException(Exception e, String msg) {
		super(msg, e);
	}
}
