package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import database.Utente;
import utility.Password;

//TODO https://docs.oracle.com/javaee/6/api/javax/enterprise/context/ConversationScoped.html <-- rivedere conversation scope
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

	// ***************** SEZIONE METODI DI SERVIZIO *****************//

	public void setUtenteLoggato(Utente utenteLoggato) {
		this.utenteLoggato = utenteLoggato;
	}

	public Utente getUtenteLoggato() {
		return utenteLoggato;
	}
	// ***************** SEZIONE LOGIN *****************//

	public boolean isAdmin() {
		if (!isLogged())
			return false;
		return this.utenteLoggato.getPermessi() == 99;
	}

	public String login() {
		Utente tmp = utenteController.getByEmail(richiestaController.getUtente().getEmail()); // controllo se nel
																								// database sono
																								// presenti le
		// credenziali
		// utenteLoggato
		if (tmp != null) {
			try {
				if (Password.check(richiestaController.getUtente().getPassword(), tmp.getPassword())) { // controllo che
																										// la password
					// inserita sia corretta
					this.utenteLoggato = tmp;
					return "home";
				}
				return "login";
			} catch (Exception e) {
				System.out.println("Errore: setUtenteLoggato(), " + richiestaController.getUtente().getEmail() + ", "
						+ richiestaController.getUtente().getPassword());
				e.printStackTrace();
				return "login";
			}
		}
		return "login";
	}

	/**
	 * Metodo per la verifica che un utenteLoggato sia loggato.
	 * 
	 * @return true se l'utenteLoggato è loggato ed è presente nel database, false
	 *         altrimenti
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

	public String logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().invalidateSession();
		return "/home.xhtml?faces-redirect=true";
	}

	/**
	 * Metodo per bloccare l'accesso agli utenti loggati che vogliono visitare la
	 * pagina login.xhtml
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
