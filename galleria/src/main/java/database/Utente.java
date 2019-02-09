package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(length=30, unique=true)
	private String nome;
	@Column(length=30)
	private String password;
	@Column(length=3)
	private Integer permessi=1;
	
	public Utente() {
		super();
	}
	
	public Utente(Integer id, String nome, String password, Integer permessi) {
		super();
		this.id = id;
		this.nome = nome;
		this.password = password;
		if(!(permessi==null)) {
			this.permessi = permessi;
		}
	}

	public Utente(Integer id, String nome, String password) {
		this.id = id;
		this.nome = nome;
		this.password = password;
	}

	public Utente(String nome, String password) {
		this.nome = nome;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", password=" + password + ", permessi=" + permessi + "]";
	}

}
