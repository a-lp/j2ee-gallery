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
public class Fotografia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String url; // non restituire la URL ma usare la servlet per convertirla in bytestream e
						// restituire quello
	@Column
	private String nome;
	@Column
	private Double dimensione;
	@Column
	private Short altezza;
	@Column
	private Short larghezza;
	@ManyToMany(cascade = { CascadeType.REMOVE}, fetch = FetchType.EAGER)
	@JoinColumn()
	private Set<Tag> categorie; // TODO si può anche fare Embeddable
	@Column
	private String descrizione;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "preferiti_utente", joinColumns = @JoinColumn(name = "fotografia_id"), inverseJoinColumns = @JoinColumn(name = "utente_id"))
	private Set<Utente> preferiti = new HashSet<Utente>();

	public Fotografia() {
		super();
	}

	public Fotografia(String url, String nome, Double dimensione, Short altezza, Short larghezza, Set<Tag> categorie,
			String descrizione) {
		super();
		this.nome = nome;
		this.url = url;
		this.dimensione = dimensione;
		this.altezza = altezza;
		this.larghezza = larghezza;
		this.descrizione = descrizione;
		this.categorie = categorie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	public String getTag() {
		String result="[";
		for(Tag t : this.categorie) {
			result+=t.getTag()+",";
		}
		result=result.substring(0,result.length()-1)+"]";
		return result;
	}

	public void setCategorie(Set<Tag> categorie) {
		if (this.categorie == null)
			this.categorie = new HashSet<Tag>();
		this.categorie = categorie;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Fotografia [id=" + id + ", url=" + url + ", nome=" + nome + ", dimensione=" + dimensione + ", altezza="
				+ altezza + ", larghezza=" + larghezza + ", categorie=" + categorie + ", descrizione=" + descrizione
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altezza == null) ? 0 : altezza.hashCode());
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((dimensione == null) ? 0 : dimensione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((larghezza == null) ? 0 : larghezza.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Fotografia other = (Fotografia) obj;
		if (altezza == null) {
			if (other.altezza != null)
				return false;
		} else if (!altezza.equals(other.altezza))
			return false;
		if (categorie == null) {
			if (other.categorie != null)
				return false;
		} else if (!categorie.equals(other.categorie))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (dimensione == null) {
			if (other.dimensione != null)
				return false;
		} else if (!dimensione.equals(other.dimensione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (larghezza == null) {
			if (other.larghezza != null)
				return false;
		} else if (!larghezza.equals(other.larghezza))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
