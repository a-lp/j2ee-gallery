package dto;

import java.util.HashSet;
import java.util.Set;

import database.Album;
import database.Tag;
import database.Utente;

public class FotografiaDTO {
	private Integer id;
	private String url;
	private String nome;
	private Set<TagDTO> categorie = new HashSet<TagDTO>();
	private String descrizione;

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

	public Set<TagDTO> getCategorie() {
		return categorie;
	}

	public void setCategorie(Set<TagDTO> categorie) {
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
		return "FotografiaDTO [id=" + id + ", url=" + url + ", nome=" + nome + ", categorie=" + categorie
				+ ", descrizione=" + descrizione + "]";
	}

}
