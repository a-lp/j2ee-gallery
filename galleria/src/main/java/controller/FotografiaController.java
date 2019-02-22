package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FotografiaDAO;
import database.Album;
import database.Fotografia;
import database.Tag;

@Named
@SessionScoped
public class FotografiaController implements Serializable {
	@Inject
	FotografiaDAO dao;
	@Inject
	TagController tagController;
	@Inject
	AlbumController albumController;
	@Inject
	RichiestaController richiestaController;
	private Fotografia fotografia = new Fotografia();

	public Fotografia getFotografia() {
		return fotografia;
	}

	public void setFotografia(Fotografia fotografia) {
		this.fotografia = fotografia;
	}

	public void elimina(Integer id) {
		this.dao.elimina(id);
	}

	public Fotografia getById() {
		if ("".equals(richiestaController.getRicerca()) || richiestaController.getRicerca() == null)
			return null;
		try {
			Fotografia f = dao.find(Integer.parseInt(richiestaController.getRicerca()));
			System.out.println(f);
			return f;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public void save() {
		Fotografia fotografia = new Fotografia();
		fotografia.setNome(richiestaController.getFotografia().getNome());
		fotografia.setDescrizione(richiestaController.getFotografia().getDescrizione());
		fotografia.setUrl(richiestaController.getFotografia().getUrl());
		String[] tag = richiestaController.getRicerca().split(",");
		System.out.println(fotografia);
		if (tag.length > 0) {
			for (String t : tag) {
				t = t.trim().toLowerCase();
				Tag tmp = new Tag(t);
				Tag result = tagController.findTag(tmp);
				if (result == null) { // se non trovo il tag, lo salvo
					tagController.save(tmp);
					result = tagController.findTag(tmp);
				}
				fotografia.getCategorie().add(result);
			}
		}
		dao.add(fotografia);
		this.fotografia = new Fotografia();
	}

	public List<Fotografia> getFotografie() {
		return dao.findAll();
	}

	public List<Fotografia> getMaxFotografie(int max) {
		return dao.findMax(max);
	}

	public List<Fotografia> getByTag() {
		if (richiestaController.getTag() == null || "".equals(richiestaController.getTag().getTag()))
			return null;
		List<Fotografia> f = dao.findByTag(richiestaController.getTag());
		if (f.size() == 0)
			return null;
		return f;
	}

	public List<Fotografia> getBySearch() {
		if (richiestaController.getRicerca() == null || "".equals(richiestaController.getRicerca()))
			return null;
		List<Fotografia> f = dao.getBySearch(richiestaController.getRicerca());
		if (f.size() == 0)
			return null;
		return f;
	}

	public void aggiungiFoto(Fotografia foto) {
		System.out.println(richiestaController.getAlbum() + " - " + foto);
		if (foto != null) {
			Album album = albumController.get(richiestaController.getAlbum().getId());
			if (album != null) {
				album.getFotografie().add(foto);
				albumController.update(album);
			} else
				System.out.println("Album vuoto: " + album);
		} else
			System.out.println("Foto vuoto: " + foto);
	}

}
