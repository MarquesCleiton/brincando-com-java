package br.com.marquescleiton.testeexception.util;

public enum MapeiaCamposEnum {
	CAMPO	("campoEntidade", 	"campoBanco"),
	NOME	("nome", 			"nome"),
	IDADE	("idade", 			"idade");
	
	private String campoEntidade;
	private String campoBanco;
	
	private MapeiaCamposEnum (String campoEntidade, String campoBanco) {
		this.campoEntidade = campoEntidade;
		this.campoBanco = campoBanco;
	}
	
	public MapeiaCamposEnum buscaCampoEntidade(String campoBanco) {
		for(MapeiaCamposEnum campo: MapeiaCamposEnum.values()){
			if(campoBanco.contains(campo.campoBanco)) {
				return campo;
			}
		}
		return null;
	}

	public String getCampoEntidade() {
		return campoEntidade;
	}

	public String getCampoBanco() {
		return campoBanco;
	}
}
