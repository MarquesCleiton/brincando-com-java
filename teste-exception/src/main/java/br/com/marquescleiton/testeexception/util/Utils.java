package br.com.marquescleiton.testeexception.util;

import java.lang.reflect.Field;

public class Utils {

	public void setCampoTruncadoParaDefault(Object entidade, String campoTruncado)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Field field = entidade.getClass().getDeclaredField(campoTruncado);

		field.setAccessible(true);

		String tipo = field.getType().toString();
		System.out.println("Tipo: " + tipo);

		if (tipo.equals("int") || tipo.equals("long") || tipo.equals("float") || tipo.equals("double")) {
			field.set(entidade, 0);
		} else if (tipo.equals("byte")) {
			byte b = 0;
			field.set(entidade, b);
		} else if (tipo.equals("short")) {
			short s = 0;
			field.set(entidade, s);
		} else if (tipo.equals("boolean")) {
			field.set(entidade, false);
		} else if (tipo.equals("char")) {
			field.set(entidade, '\u0000');
		} else if (tipo.equals("class java.lang.String")) {
			field.set(entidade, "");
		} else {
			field.set(entidade, null);
		}
		
		field.setAccessible(false);
	}
}
