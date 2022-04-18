package br.com.marquescleiton.entity.enums;

public enum MapeiaClientesEnum {
	CAMPO("CLIENTE1", "CLIENTE2",0),
	NOME ("nome" ,"nomeCliente", 1),
	IDADE("idade","idadeCliente", 2),
	CPF  ("cpf"  ,"cpfCliente", 3);
	
	private String campoC1;
	private String CampoC2;
	private int id;
	
	MapeiaClientesEnum(String cliente1, String cliente2, int id){
		this.campoC1 = cliente1;
		this.CampoC2 = cliente2;
		this.id = id;
	}
	
	public MapeiaClientesEnum buscaCampoCliente2(int id) {
		
		for(MapeiaClientesEnum cliente: MapeiaClientesEnum.values()) {
			if(cliente.getId() == id) {
				return cliente;
			}
		}
		return null;
	}

	public String getCampoC1() {
		return campoC1;
	}

	public String getCampoC2() {
		return CampoC2;
	}
	
	public int getId() {
		return id;
	}

	
	
}
