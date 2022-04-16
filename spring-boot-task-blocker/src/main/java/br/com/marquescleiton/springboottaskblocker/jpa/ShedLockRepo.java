package br.com.marquescleiton.springboottaskblocker.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShedLockRepo extends JpaRepository<ShedLockJpa, String>{
	
	public ShedLockJpa findByName(String nome);

}
