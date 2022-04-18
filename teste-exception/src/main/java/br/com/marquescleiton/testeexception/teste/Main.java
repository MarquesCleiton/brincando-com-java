package br.com.marquescleiton.testeexception.teste;

import java.lang.reflect.Field;

import br.com.marquescleiton.testeexception.jpa.ClienteJPA;

public class Main {
	public static void main(String[]args) throws IllegalArgumentException, IllegalAccessException {
		

		
		ClienteJPA cliente = new ClienteJPA();
		cliente.setNome("Cleiton");
		cliente.setIdade(23);
		
		System.out.println(cliente.getNome());
		System.out.println(cliente.getIdade());
		
		setDefault(cliente);
		
		System.out.println(cliente.getNome());
		System.out.println(cliente.getIdade());
	}
	
	private static void setDefault(Object obj) throws IllegalArgumentException, IllegalAccessException{
		
		Field filds[] = obj.getClass().getDeclaredFields();
		
		for(Field field : filds) {
			field.setAccessible(true);
			
			String tipo = field.getType().toString();
			System.out.println("Tipo: " + tipo);
			
			if(tipo.equals("int") || tipo.equals("long") || tipo.equals("float") || tipo.equals("double")) {
				field.set(obj, 0);
			}else if(tipo.equals("byte")) {
				byte b = 0;
				field.set(obj, b);
			}else if(tipo.equals("short")){
				short s = 0;
				field.set(obj, s);
			}else if(tipo.equals("boolean")){
				field.set(obj, false);
			}else if(tipo.equals("char")){
				field.set(obj, '\u0000');
			}else if(tipo.equals("class java.lang.String")) {
				field.set(obj, "");
			}else {
				field.set(obj, null);
			}
			field.setAccessible(false);
		}
	}	
}
