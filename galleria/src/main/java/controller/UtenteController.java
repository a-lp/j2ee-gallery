package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import DAO.UtenteDAO;
import database.Utente;

@Named
@SessionScoped
public class UtenteController implements Serializable  {
	@Inject
	UtenteDAO dao;

	private Utente utente = new Utente();

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public void save() {
		dao.add(utente);
		utente = new Utente();
	}

	public List<Utente> getUtenti() {
		return dao.findAll();
	}

}
