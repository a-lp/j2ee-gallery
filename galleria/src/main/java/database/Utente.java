package database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "preferiti_utente", joinColumns = @JoinColumn(name = "utente_id"), inverseJoinColumns = @JoinColumn(name = "fotografia_id"))
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((permessi == null) ? 0 : permessi.hashCode());
		result = prime * result + ((preferiti == null) ? 0 : preferiti.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permessi == null) {
			if (other.permessi != null)
				return false;
		} else if (!permessi.equals(other.permessi))
			return false;
		if (preferiti == null) {
			if (other.preferiti != null)
				return false;
		} else if (!preferiti.equals(other.preferiti))
			return false;
		return true;
	}

}
