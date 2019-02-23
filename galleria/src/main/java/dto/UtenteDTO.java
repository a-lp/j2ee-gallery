package dto;

import java.util.HashSet;
import java.util.Set;

public class UtenteDTO {
	private Integer id;
	private String email;
	private String password;
	private Short permessi = 1;
	private Set<FotografiaDTO> preferiti = new HashSet<FotografiaDTO>();
	private Set<AlbumDTO> album = new HashSet<AlbumDTO>();

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

	public Set<FotografiaDTO> getPreferiti() {
		return preferiti;
	}

	public void setPreferiti(Set<FotografiaDTO> preferiti) {
		this.preferiti = preferiti;
	}

	public Set<AlbumDTO> getAlbum() {
		return album;
	}

	public void setAlbum(Set<AlbumDTO> album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return "UtenteDTO [id=" + id + ", email=" + email + ", password=" + password + ", permessi=" + permessi
				+ ", preferiti=" + preferiti + ", album=" + album + "]";
	}

}
