package controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import DAO.FotografiaDAO;
import database.Fotografia;

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
