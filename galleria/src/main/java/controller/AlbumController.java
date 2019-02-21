package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import dao.AlbumDAO;
import database.Album;
import database.Fotografia;

@Named
@SessionScoped
public class AlbumController implements Serializable {
	@Inject
	AlbumDAO dao;
	@Inject
	SessionController sessione;

	private Album album = new Album();

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public void save() {
		dao.add(album);
		album = new Album();
	}

	public List<Album> getAlbums() {
		return dao.getAllAlbum();
	}

	public void aggiungiFoto(Fotografia foto) {
		if (foto != null) {
			Album album = dao.get(sessione.getAlbum().getId());
			album.setFotografie(dao.getFotografie(album));
			if (album != null) {
				album.getFotografie().add(foto);
				dao.update(album);
				this.sessione.setAlbum(null);
			} else
				System.out.println("Album vuoto: " + album);
		} else
			System.out.println("Foto vuoto: " + foto);
	}

}
