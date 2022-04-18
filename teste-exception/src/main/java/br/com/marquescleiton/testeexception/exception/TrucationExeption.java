package br.com.marquescleiton.testeexception.exception;

import java.util.ArrayList;

public class TrucationExeption extends Exception{

	private static final long serialVersionUID = 3710454218089498124L;
	private ArrayList<String> listaErros;
	
	public TrucationExeption(String msg, ArrayList<String> listaErros) {
		super(msg);
		this.listaErros = listaErros;
	}
	
	public ArrayList<String> getListaErros(){
		return this.listaErros;
	}

}
