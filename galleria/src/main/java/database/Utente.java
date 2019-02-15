package database;

import java.util.HashSet;
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
	@Column(length = 30, unique = true, nullable = false)
	private String email;
	@Column(length = 512, nullable = false)
	private String password;
	@Column
	private Short permessi = 1;
	@OneToMany // TODO definire cascade e fetch.lazy
	@JoinColumn
	private Set<Fotografia> preferiti = new HashSet<Fotografia>();

	public Utente() {
		super();
	}

	public Utente(Utente u) {
		super();
		this.id = u.getId();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.permessi = u.getPermessi();
		this.preferiti = u.getPreferiti();
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
