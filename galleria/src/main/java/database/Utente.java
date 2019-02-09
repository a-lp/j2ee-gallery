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
	private String nickname;
	@Column(length = 30)
	private String password;
	@Column(length = 3)
	private Integer permessi = 1;
	@OneToMany
	@JoinColumn
	private Set<Fotografie> preferiti;

	public Set<Fotografie> getPreferiti() {
		return preferiti;
	}

	public void setPreferiti(Set<Fotografie> preferiti) {
		this.preferiti = preferiti;
	}

	public Utente() {
		super();
	}

	public Utente(Integer id, String nickname, String password, Integer permessi) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		if (!(permessi == null)) {
			this.permessi = permessi;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getnickname() {
		return nickname;
	}

	public void setnickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPermessi() {
		return permessi;
	}

	public void setPermessi(int permessi) {
		this.permessi = permessi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nickname=" + nickname + ", password=" + password + ", permessi=" + permessi
				+ "]";
	}

}
