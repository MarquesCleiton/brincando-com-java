package br.com.marquescleiton.springboottaskblocker.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHEDLOCK ")
public class ShedLockJpa {
	@Id
	@Column(name = "name", length = 2)
	private String name;
	
	@Column(name = "lock_until")
	private LocalDateTime lock_until;
	
	@Column(name = "locked_at")
	private LocalDateTime locked_at; 
	
	@Column(name = "locked_by", length = 255)
	private String locked_by;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getLock_until() {
		return lock_until;
	}

	public void setLock_until(LocalDateTime lock_until) {
		this.lock_until = lock_until;
	}

	public LocalDateTime getLocked_at() {
		return locked_at;
	}

	public void setLocked_at(LocalDateTime locked_at) {
		this.locked_at = locked_at;
	}

	public String getLocked_by() {
		return locked_by;
	}

	public void setLocked_by(String locked_by) {
		this.locked_by = locked_by;
	}

}
