package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UtenteDAO;
import database.Utente;

@Named
@SessionScoped
public class UtenteController implements Serializable {
	@Inject
	UtenteDAO dao;
	private Utente utente = new Utente();
	private String ricerca="";

	public String getRicerca() {
		return ricerca;
	}
	
	public boolean isRicercaCompleta() {
		return "".equals(ricerca);
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

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

	public Utente getByEmail() {
		return dao.findByEmail(ricerca);
	}

}
