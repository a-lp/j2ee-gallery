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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@NamedEntityGraph(name = "fotografiaLazy", attributeNodes = { @NamedAttributeNode("preferiti"),
		@NamedAttributeNode("album") })
public class Fotografia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String url;
	@Column
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String nome;
	// le categorie vengono inserite insieme alle foto e rimangono invariate, quindi
	// posso lasciare il cascade a REMOVE.
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn
	@IndexedEmbedded
	private Set<Tag> categorie = new HashSet<Tag>();
	@Column
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String descrizione;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "preferiti_utente", joinColumns = @JoinColumn(name = "fotografia_id"), inverseJoinColumns = @JoinColumn(name = "utente_id"))
	private Set<Utente> preferiti = new HashSet<Utente>();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "album_fotografia", joinColumns = @JoinColumn(name = "fotografia_id"), inverseJoinColumns = @JoinColumn(name = "album_id"))
	private Set<Album> album = new HashSet<Album>();

	public Fotografia() {
		super();
	}

	public Fotografia(String url, String nome, Double dimensione, Set<Tag> categorie, String descrizione) {
		super();
		this.nome = nome;
		this.url = url;
		this.descrizione = descrizione;
		this.categorie = categorie;
	}

	public Set<Utente> getPreferiti() {
		return preferiti;
	}

	public void setPreferiti(Set<Utente> preferiti) {
		this.preferiti = preferiti;
	}

	public Set<Album> getAlbum() {
		return album;
	}

	public void setAlbum(Set<Album> album) {
		this.album = album;
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

	public Set<Tag> getCategorie() {
		return categorie;
	}

	public String getTag() {
		if (this.categorie.size() == 0)
			return "";
		String result = "[";
		for (Tag t : this.categorie) {
			result += t.getTag() + ",";
		}
		result = result.substring(0, result.length() - 1) + "]";
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fotografia [id=" + id + ", url=" + url + ", nome=" + nome + ", categorie=" + categorie
				+ ", descrizione=" + descrizione + ", preferiti=" + preferiti + ", album=" + album + "]";
	}

}
