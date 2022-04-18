package br.com.marquescleiton;

import java.lang.reflect.Field;

import br.com.marquescleiton.entity.ClienteEntity1;
import br.com.marquescleiton.entity.ClienteEntity2;
import br.com.marquescleiton.entity.enums.MapeiaClientesEnum;

public class Main {

	public static void main(String[]args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		int id = 3;
		MapeiaClientesEnum myEnum = MapeiaClientesEnum.CAMPO.buscaCampoCliente2(id);
		
		//MQ
		ClienteEntity1 c1 = new ClienteEntity1();
		c1.setNome("Cleiton");
		c1.setIdade(23);
		c1.setCpf(123456);

		//JPA
		ClienteEntity2 c2 = new ClienteEntity2();
			
		//Jesus(c1, c2, 3);
		
		ajustaCampoTruncado(c2, "idadeCliente", 55);
		
		System.out.println(c2.getNomeCliente());
		System.out.println(c2.getIdadeCliente());
		System.out.println(c2.getCpfCliente());
		
		MapeiaClientesEnum myEnum2[] = MapeiaClientesEnum.values();
		
		for(int i = 1; i< myEnum2.length; i++){
			populaObjeto(c1, c2, myEnum2[i].getCampoC1(), myEnum2[i].getCampoC2());
		}
		System.out.println();
		System.out.println(c2.getNomeCliente());
		System.out.println(c2.getIdadeCliente());
		System.out.println(c2.getCpfCliente());
	}
	
	private static void seCamposTruncados(Object Obj1, Object Obj2, int campoTruncado) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		MapeiaClientesEnum myEnum = MapeiaClientesEnum.CAMPO.buscaCampoCliente2(campoTruncado);
		
		Field [] campos = Obj1.getClass().getDeclaredFields();
		
		for(Field campo: campos) {
			campo.setAccessible(true);			
			if(campo.getName().equals(myEnum.getCampoC1())) {
				
				Field campoc2 = Obj2.getClass().getDeclaredField(myEnum.getCampoC2());
				campoc2.setAccessible(true);
				
				Object valor = campo.get(Obj1); 
				campoc2.set(Obj2, valor);
				
			}
		}
	}
	
	private static void ajustaCampoTruncado(Object objeto, String campoTruncado, Object novoValor) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field campo = objeto.getClass().getDeclaredField(campoTruncado);
		campo.setAccessible(true);
		campo.set(objeto, novoValor);
		campo.setAccessible(false);
	}
	
	private static void populaObjeto(Object objeto1, Object objeto2, String campoObjeto1, String campoObjeto2) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field campoObj1 = objeto1.getClass().getDeclaredField(campoObjeto1);
		campoObj1.setAccessible(true);
		
		Field campoObj2 = objeto2.getClass().getDeclaredField(campoObjeto2);
		campoObj2.setAccessible(true);
		
		Object valor = campoObj1.get(objeto1);
		System.out.println(campoObj2.getType());
		campoObj2.set(objeto2, valor);
	}
}
