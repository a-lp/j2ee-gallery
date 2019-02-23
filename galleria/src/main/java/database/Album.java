package database;

import java.util.HashSet;
import java.util.Set;

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
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String nome;
	@Column
	private String descrizione;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "album_fotografia", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "fotografia_id"))
	private Set<Fotografia> fotografie = new HashSet<Fotografia>();

	public Album() {
		super();
	}

	public Album(Integer id, String nome, String descrizione, Set<Utente> utenti, Set<Fotografia> fotografie) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.fotografie = fotografie;
	}

	public Set<Fotografia> getFotografie() {
		return fotografie;
	}

	public void setFotografie(Set<Fotografia> fotografie) {
		this.fotografie = fotografie;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	@Override
	public String toString() {
		return "Album [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Album other = (Album) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
