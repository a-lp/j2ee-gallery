package database;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Fotografia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String nome;
	@Column
	private Double dimensione;
	@Column
	private Short altezza;
	@Column
	private Short larghezza;
	@ManyToMany
	@JoinColumn
	private List<Tag> tag;
	
	public Double getDimensione() {
		return dimensione;
	}

	public void setDimensione(Double dimensione) {
		this.dimensione = dimensione;
	}

	public Short getAltezza() {
		return altezza;
	}

	public void setAltezza(Short altezza) {
		this.altezza = altezza;
	}

	public Short getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(Short larghezza) {
		this.larghezza = larghezza;
	}

	public Integer getId() {
		return id;
	}

	public Fotografia() {
		super();
	}

	public Fotografia(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
