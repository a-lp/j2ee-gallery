package dto;

import java.util.HashSet;
import java.util.Set;

public class AlbumDTO {
	private Integer id;
	private String nome;
	private String descrizione;
	private Set<FotografiaDTO> fotografie = new HashSet<FotografiaDTO>();

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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<FotografiaDTO> getFotografie() {
		return fotografie;
	}

	public void setFotografie(Set<FotografiaDTO> fotografie) {
		this.fotografie = fotografie;
	}

	@Override
	public String toString() {
		return "AlbumDTO [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", fotografie=" + fotografie
				+ "]";
	}

}
