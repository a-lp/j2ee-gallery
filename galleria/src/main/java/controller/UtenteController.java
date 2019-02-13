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

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String save() {
		if (!"".equals(this.utente.getEmail()) && !"".equals(this.utente.getPassword()) && this.isValoreUnico()) {
			dao.add(utente); // aggiungo nuovo utente
			utente = new Utente(); // creo un nuovo utente vuoto
			return "registrazione"; // torno alla pagina di registrazione
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
		return dao.findByEmail(this.utente.getEmail());
	}

	public boolean isValoreUnico() {
		return dao.findByEmail(this.utente.getEmail()) == null;
	}
	
	public Short getPermessi() {
		return dao.getPermessi(this.utente.getEmail());
	}


}
