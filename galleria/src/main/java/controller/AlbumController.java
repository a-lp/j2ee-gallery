package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlbumDAO;
import database.Album;
import database.Fotografia;

@Named
@SessionScoped
public class AlbumController implements Serializable {
	@Inject
	AlbumDAO dao;
	@Inject
	RichiestaController richiestaController;
	private Album album = new Album();

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public void save() {
		setAlbum(richiestaController.getAlbum());
		if (dao.getByName(this.album.getNome()) == null) {
			dao.add(album);
		}
		this.album = new Album();
	}

	public List<Album> getAlbums() {
		return dao.getAllAlbum();
	}

	public void aggiungiFoto(Fotografia foto) {
		if (foto != null) {
			Album album = dao.get(this.album.getId());
			album.setFotografie(dao.getFotografie(album));
			if (album != null) {
				album.getFotografie().add(foto);
				dao.update(album);
			}
		}
	}

	public void rimuoviFoto(Fotografia foto) {
		if (foto != null) {
			Album album = dao.get(this.album.getId());
			album.setFotografie(dao.getFotografie(album));
			if (album != null) {
				album.getFotografie().remove(foto);
				dao.update(album);
			}
		}
	}

	public boolean contains(Fotografia p) {
		Album album = dao.get(this.album.getId());
		album.setFotografie(dao.getFotografie(album));
		return album.getFotografie().contains(p);
	}

	public List<Fotografia> getFotoByAlbum() {
		Album album = dao.get(this.album.getId());
		album.setFotografie(dao.getFotografie(album));
		System.out.println(album.getFotografie());
		return album.getFotografie();
	}

	public void update(Album album2) {
		dao.update(album2);
	}

	public Album get(Integer id) {
		return dao.get(id);
	}

}
