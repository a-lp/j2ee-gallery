package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FotografiaDAO;
import database.Fotografia;

@Named
@SessionScoped
public class FotografiaController implements Serializable  {
	@Inject
	FotografiaDAO dao;

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
