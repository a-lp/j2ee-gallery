package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import database.Utente;
import utility.Password;

/**
 * Classe per la gestione del login utente.La classe è di tipo SessionScoped e
 * può essere richiamata dalle pagine xhtml.
 * 
 * @author Armando La Placa
 *
 */

@Named
@SessionScoped
public class SessionController implements Serializable {
	@Inject
	RichiestaController richiestaController;
	@Inject
	UtenteController utenteController;
	/**
	 * Utente loggato.
	 */
	private Utente utenteLoggato;

	public void setUtenteLoggato(Utente utenteLoggato) {
		this.utenteLoggato = utenteLoggato;
	}

	public Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	/**
	 * Controllo dei permessi dell'utente loggato. 99 è admin, 1 è utente.
	 * 
	 * @return true se l'utente è admin, false altrimenti.
	 */
	public boolean isAdmin() {
		// se l'utente non è loggato, restituisco false
		if (!isLogged())
			return false;
		return this.utenteLoggato.getPermessi() == 99;
	}

	/**
	 * Metodo per la gestione dell'accesso da parte dell'utente. Se l'utente è
	 * registrato nel database, si controlla che le credenziali inserite siano
	 * corrette quindi la si assegna alla variabile utenteLoggato della classe
	 * sessione.
	 * 
	 * @return Pagina di ritorno: home.xhtml se il login va a buon fine, login.xhtml
	 *         altrimenti.
	 */
	public String login() {
		Utente tmp = utenteController.getByEmail(richiestaController.getUtente().getEmail());
		if (tmp != null) {
			try {
				// controllo della password tramite libreria esterna
				if (Password.check(richiestaController.getUtente().getPassword(), tmp.getPassword())) {
					this.utenteLoggato = tmp;
					return "home";
				}
				return "login";
			} catch (Exception e) {
				return "login";
			}
		}
		return "login";

	}

	/**
	 * Metodo per la verifica che un utenteLoggato sia loggato.
	 * 
	 * @return true se l'utenteLoggato è loggato ed è presente nel database, false
	 *         altrimenti.
	 */
	public boolean isLogged() {
		if (this.utenteLoggato != null) {
			if (utenteController.getById(utenteLoggato.getId()) == null) {
				logout();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Metodo per la pulizia della sessione.
	 * 
	 * @return Si viene rediretti alla pagina home.xhtml.
	 */
	public String logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().invalidateSession();
		return "/home.xhtml?faces-redirect=true";
	}

	/**
	 * Metodo per bloccare l'accesso agli utenti loggati che vogliono visitare la
	 * pagina login.xhtml. Se l'utente è già loggato, viene rispedito alla pagina
	 * home.xhtml.
	 */
	public void checkIsLogged() {
		if (this.utenteLoggato != null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo per bloccare l'accesso alla pagina admin.xhtml agli utenti non admin.
	 */
	public void checkIsAdmin() {
		if (!this.isAdmin()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
