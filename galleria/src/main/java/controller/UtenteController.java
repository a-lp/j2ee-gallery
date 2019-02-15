package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UtenteDAO;
import database.Utente;
import login.RichiestaUtente;

@Named
@SessionScoped
public class UtenteController implements Serializable {
	@Inject
	UtenteDAO dao;
	@Inject
	RichiestaUtente richiesta;
	
	public void elimina(String email) {
		dao.elimina(email);
	}

	public String save() {
		if (!"".equals(richiesta.getEmail()) && !"".equals(richiesta.getPassword()) && this.isValoreUnico()) {
			Utente u = new Utente();
			u.setEmail(richiesta.getEmail());
			u.setPassword(richiesta.getPassword());
			dao.add(u); // aggiungo nuovo richiesta
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

	public Utente getByEmail() {
		return dao.findByEmail(richiesta.getEmail());
	}

	public boolean isValoreUnico() {
		return dao.findByEmail(richiesta.getEmail()) == null;
	}

	public Short getPermessi() {
		return dao.getPermessi(richiesta.getEmail());
	}

}
