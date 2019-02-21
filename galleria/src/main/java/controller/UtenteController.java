package controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UtenteDAO;
import database.Album;
import database.Fotografia;
import database.Utente;

@Named
@SessionScoped
public class UtenteController implements Serializable {
	@Inject
	UtenteDAO dao;
	@Inject
	SessionController sessione;

	public void elimina(String email) {
		dao.elimina(email);
	}

	public String save(Utente utente) {
		if (utente != null && isValoreUnico(utente)) {
			dao.add(utente);
			return "home";
		}
		return "registrazione";
	}

	public List<Utente> getUtenti() {
		List<Utente> result = dao.findAll();
		if (result.size() == 0)
			result = null;
		return result;
	}

	public Utente getByEmail(String email) {
		if (email == null && "".equals(email))
			return null;
		return dao.findByEmail(email);
	}

	public Utente getById(Integer id) {
		if (id == null && id > 0)
			return null;
		return dao.find(id);
	}

	public boolean isValoreUnico(Utente utente) {
		return dao.findByEmail(utente.getEmail()) == null;
	}

	public Short getPermessi() {
		return dao.getPermessi(sessione.getUtenteLoggato().getEmail());
	}

	public void aggiungiPreferiti(Fotografia foto) {
		if (foto != null) {
			Utente utente = dao.find(sessione.getUtenteLoggato().getId());
			utente.setPreferiti(dao.getPreferiti(utente));
			if (utente != null) {
				utente.getPreferiti().add(foto);
				dao.update(utente);
				this.sessione.setUtenteLoggato(utente);
			}
		}
	}

	public void eliminaPreferito(Fotografia foto) {
		if (foto != null) {
			Utente utente = dao.find(sessione.getUtenteLoggato().getId());
			utente.setPreferiti(dao.getPreferiti(utente));
			if (utente != null) {
				utente.getPreferiti().remove(foto);
				dao.update(utente);
				this.sessione.setUtenteLoggato(utente);
			}
		}
	}

	public Set<Fotografia> getPreferiti() {
		return dao.getPreferiti(sessione.getUtenteLoggato());
	}

	public boolean isPreferito(Fotografia f) {
		return (getPreferiti().contains(f));
	}

	public Set<Album> getAlbum() {
		return dao.getAlbum(this.sessione.getUtenteLoggato());
	}
}
