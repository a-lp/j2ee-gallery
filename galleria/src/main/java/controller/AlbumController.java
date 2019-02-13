package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlbumDAO;
import database.Album;

@Named
@SessionScoped
public class AlbumController implements Serializable {
	@Inject
	AlbumDAO dao;

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
		return dao.findAll();
	}
}
