package br.com.marquescleiton.testeexception.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.marquescleiton.testeexception.exception.TrucationExeption;
import br.com.marquescleiton.testeexception.jpa.ClienteJPA;
import br.com.marquescleiton.testeexception.jpa.ClienteRepo;
import br.com.marquescleiton.testeexception.util.MapeiaCamposEnum;
import br.com.marquescleiton.testeexception.util.Utils;

@RestController("/")
public class TesteControler {

	@Autowired
	private ClienteRepo clienteRepo;

	@GetMapping("{nome}")
	public String index(@PathVariable String nome) throws TrucationExeption, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ClienteJPA cliente = new ClienteJPA();
		try {

			cliente.setNome(nome);
			cliente.setIdade(2301);

			salvaCliente(cliente);
			System.out.println("Chamado");
			return "Olá " + nome;
		} catch (TrucationExeption e) {
			salvaClienteTrucationException(cliente, null);
		}
		return "Deu ruim!";
	}

	private void salvaCliente(ClienteJPA cliente) throws TrucationExeption {
		try {
			clienteRepo.save(cliente);
		} catch (DataIntegrityViolationException e) {
			throw new TrucationExeption("Personal Problema: " + e.getCause().getCause().getMessage(), null);
		}
	}

	private void salvaClienteTrucationException(ClienteJPA cliente, ArrayList<String> camposTruncados) 
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, TrucationExeption {
		
		camposTruncados = (camposTruncados == null) ? new ArrayList<String>() : camposTruncados;
		
		try {
			clienteRepo.save(cliente);
		} catch (DataIntegrityViolationException e) {
			
			Utils util = new Utils();
			String msg = e.getCause().getCause().getMessage().split(";")[0];
			camposTruncados.add(msg);
			
			MapeiaCamposEnum enu = MapeiaCamposEnum.CAMPO.buscaCampoEntidade(msg.toLowerCase());
			
			util.setCampoTruncadoParaDefault(cliente, enu.getCampoEntidade());
			
			System.out.println("Problema: " + msg);

			salvaClienteTrucationException(cliente, camposTruncados);
		}
	}
}
