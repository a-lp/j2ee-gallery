package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FotografiaDAO;
import database.Fotografia;
import request.RichiestaUtente;

@Named
@SessionScoped
public class FotografiaController implements Serializable {
	@Inject
	FotografiaDAO dao;
	@Inject
	RichiestaUtente richiesta;

	public void elimina(Integer id) {
		this.dao.elimina(id);
	}

	public Fotografia getById() {
		if("".equals(richiesta.getRicerca()) || richiesta.getRicerca()==null)
			return null;
		return this.dao.find(Integer.parseInt(richiesta.getRicerca())); // TODO controllare che la ricerca sia un numero
	}

	private Fotografia fotografia = new Fotografia();

	public Fotografia getFotografia() {
		return fotografia;
	}

	public void setFotografia(Fotografia fotografia) {
		this.fotografia = fotografia;
	}

	public void save() {
		dao.add(fotografia);
		fotografia = new Fotografia();
	}

	public List<Fotografia> getFotografie() {
		return dao.findAll();
	}
}
