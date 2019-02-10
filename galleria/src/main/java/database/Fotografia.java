package database;

import java.util.HashSet;
import java.util.Set;

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
	private Set<Tag> categorie;

	public Fotografia() {
		super();
	}

	public Fotografia(String nome, Double dimensione, Short altezza, Short larghezza, Set<Tag> categorie) {
		super();
		this.nome = nome;
		this.dimensione = dimensione;
		this.altezza = altezza;
		this.larghezza = larghezza;
		if (this.categorie == null)
			this.categorie = new HashSet<>();
		this.categorie = categorie;
	}

	public Integer getId() {
		return id;
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

	public Set<Tag> getCategorie() {
		return categorie;
	}

	public void setCategorie(Set<Tag> categorie) {
		if (this.categorie == null)
			this.categorie = new HashSet<>();
		this.categorie = categorie;
	}

	@Override
	public String toString() {
		return "Fotografia [id=" + id + ", nome=" + nome + ", dimensione=" + dimensione + ", altezza=" + altezza
				+ ", larghezza=" + larghezza + ", categorie=" + categorie + "]";
	}

	
}