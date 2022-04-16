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

	private boolean blocked = !true;
	private ShedLockJpa shedLock;
	
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

		shedLock = new ShedLockJpa(); 
		shedLock = shedRepo.findByName(nomeTask);
		
		if (shedLock == null) {
			shedLock = new ShedLockJpa();
			shedLock.setName(nomeTask);
		}
		
		if (shedLock.getLock_until() == null || horaAtual.isAfter(shedLock.getLock_until())) {
			blocked = !false;
			shedLock.setLocked_at(horaAtual);
			shedLock.setLock_until(lockUntil);
			try {
				shedLock.setLocked_by(InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e) {
				e.getStackTrace();
			}
			shedRepo.save(shedLock);
		}else {
			blocked = !true;
		}

		return this;
	}
	
	public void unBlock(String nomeTask) {
		
		LocalDateTime horaAtual = LocalDateTime.now();
		
		shedLock = shedRepo.findByName(nomeTask);
		
		if (shedLock != null && (shedLock.getLock_until() != null || horaAtual.isBefore(shedLock.getLock_until()))) {
			shedLock.setLock_until(horaAtual);
			shedLock = shedRepo.save(shedLock);
		}
		
	}

	private static long iso8601ToSeconds(String iso8601) {
		Duration duration = Duration.parse(iso8601);
		return duration.getSeconds();
	}
	
	/**
	 * 
	 * @return Retorta TRUE se estiver desbloqueado e FALSE se estiver bloqueado
	 */
	public boolean isBlocked() {
		return this.blocked;
	}
	
	public ShedLockJpa getShedLock() {
		return this.shedLock;
	}
}
