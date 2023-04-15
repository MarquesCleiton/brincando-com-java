package br.com.marquescleiton.springboottaskblocker.blocker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.marquescleiton.springboottaskblocker.jpa.ShedLockJpa;
import br.com.marquescleiton.springboottaskblocker.jpa.ShedLockRepo;

@Component
public class Blocker {
	
	@Autowired
	private ShedLockRepo shedRepo;
	
	/**
	 * Cria um bloqueio na base com o nome da tarefa.
	 * 
	 * Se a tarefa não existir é criada uma nova na base
	 * com o name igual o nomeTask, locked_at igual a data/hora atual, o lockUntil
	 * com a data/hora atual + lockAtMostFor e Locked_by com o nome do host que 
	 * fez o bloqueio
	 * 
	 * @param nomeTask nome da tarefa que será feio o bloqueio
	 * @param lockAtMostFor dita o quanto tempo a tarefa será bloqueada
	 * @return a própria classe Locker, podendo assim chamar 
	 * outros atributos/metodos da classe
	 */
	public Blocker block(String nomeTask, String lockAtMostFor) {

		LocalDateTime horaAtual = LocalDateTime.now();
		LocalDateTime lockUntil = horaAtual.plusSeconds(iso8601ToSeconds(lockAtMostFor));
		
		ShedLockJpa shedLock = new ShedLockJpa(); 
		shedLock = shedRepo.findByName(nomeTask);
		
		if (shedLock == null) {
			shedLock = new ShedLockJpa();
			shedLock.setName(nomeTask);
		}
		
		if (isBlocked(shedLock) == false) {
			shedLock.setLocked_at(horaAtual);
			shedLock.setLock_until(lockUntil);
			try {
				shedLock.setLocked_by(InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e) {
				e.getStackTrace();
			}
			shedRepo.save(shedLock);
		}

		return this;
	}
	
	public void unBlock(String nomeTask) {
		
		LocalDateTime horaAtual = LocalDateTime.now();
		ShedLockJpa shedLock = shedRepo.findByName(nomeTask);
		
		if (isBlocked(shedLock) == true) {
			shedLock.setLock_until(horaAtual);
			shedLock = shedRepo.save(shedLock);
		}	
	}

	private static long iso8601ToSeconds(String iso8601) {
		Duration duration = Duration.parse(iso8601);
		return duration.getSeconds();
	}
	
	/**
	 * Verifica na base se a tarefa com o nome especificado em nomeTask
	 * está bloqueada ou não
	 * 
	 * @param nomeTask : nome da tarefa que será feito a busca
	 * 
	 * @return TRUE se estiver BLOQUEADO </br> 
	 * FALSE se estiver DESBLOQUEADO
	 * 
	 */
	public boolean isBlocked(String nomeTask) {
		
		ShedLockJpa shedLock = shedRepo.findByName(nomeTask);
				
		return isBlocked(shedLock);
	}
	
	/**
	 * Verifica se a tarefa dentro na entidade shedLock se encontra bloqueada ou não
	 * 
	 * @param shedLock : entidade com a tarefa
	 * 
	 * @return TRUE se estiver BLOQUEADO </br> 
	 * FALSE se estiver DESBLOQUEADO
	 * 
	 */
	private boolean isBlocked (ShedLockJpa shedLock) {
		LocalDateTime horaAtual = LocalDateTime.now();
		
		if (shedLock == null || shedLock.getLock_until() == null || horaAtual.isAfter(shedLock.getLock_until())) {
			return false;
		}
		
		return true;
	}
}
