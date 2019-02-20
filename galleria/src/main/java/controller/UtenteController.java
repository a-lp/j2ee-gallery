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

	public String save(Utente u) {
		if (u != null && isValoreUnico(u)) {
			dao.add(u);
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

	public boolean isValoreUnico(Utente u) {
		return dao.findByEmail(u.getEmail()) == null;
	}

	public Short getPermessi() {
		return dao.getPermessi(sessione.getUtenteLoggato().getEmail());
	}

	public void aggiungiPreferiti(Fotografia foto) {
		Utente u = dao.find(sessione.getUtenteLoggato().getId());
		u.getPreferiti().add(foto);
		dao.update(u);
		this.sessione.setUtenteLoggato(u);
		this.sessione.setRicerca("");
	}

	public void eliminaPreferito(Fotografia foto) {
		Utente u = dao.find(sessione.getUtenteLoggato().getId());
		u.getPreferiti().remove(foto);
		dao.update(u);
		this.sessione.setUtenteLoggato(u);
	}

	public Set<Fotografia> getPreferiti() {
		return dao.getPreferiti(sessione.getUtenteLoggato());
	}

	public boolean isPreferito(Fotografia f) {
		return (dao.find(this.sessione.getUtenteLoggato().getId()).getPreferiti().contains(f)
				|| this.sessione.getUtenteLoggato().getPreferiti().contains(f));
	}

	public Set<Album> getAlbum() {
		return dao.getAlbum(this.sessione.getUtenteLoggato());
	}
}
