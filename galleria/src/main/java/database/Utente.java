package database;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 30, unique = true)
	private String email;
	@Column(length = 30)
	private String password; // TODO: cifrare le password (HASH va bene)
	@Column
	private Short permessi = 1;
	@OneToMany // TODO definire cascade e fetch.lazy
	@JoinColumn
	private Set<Fotografia> preferiti;
	
	public Utente() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getPermessi() {
		return permessi;
	}
	public void setPermessi(Short permessi) {
		this.permessi = permessi;
	}
	public Set<Fotografia> getPreferiti() {
		return preferiti;
	}
	public void setPreferiti(Set<Fotografia> preferiti) {
		this.preferiti = preferiti;
	}
	
}
