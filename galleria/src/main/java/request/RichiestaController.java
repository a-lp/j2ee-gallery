package request;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UtenteDAO;
import database.Utente;
import utility.Password;

@Named
@SessionScoped
public class RichiestaController implements Serializable {
	@Inject
	RichiestaUtente richiestaUtente; // richiesta dell'utente
	@Inject
	UtenteDAO dao; // classe per l'esecuzione di query nel database
	private Utente utenteLoggato;

	public boolean isAdmin() {
		if (!isLogged())
			return false;
		return this.utenteLoggato.getPermessi() == 99;
	}

	public Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public String setUtenteLoggato() {
		Utente tmp = dao.findByEmail(richiestaUtente.getEmail()); // controllo se nel database sono presenti le
																	// credenziali utente
		if (tmp != null) {
			try {
				if (Password.check(richiestaUtente.getPassword(), tmp.getPassword())) { // controllo che la password
																						// inserita sia corretta
					this.utenteLoggato = new Utente(tmp); // instanzio l'utente loggato a partire da quello trovato
															// dalla query precedente
				}
				return "home";
			} catch (Exception e) {
				e.printStackTrace();
				return "login";
			}
		}
		return "login";
	}

	/**
	 * Metodo per la verifica che un utente sia loggato.
	 * 
	 * @return true se l'utente è loggato ed è presente nel database, false
	 *         altrimenti
	 */
	public boolean isLogged() {
		if (this.utenteLoggato != null && (dao.findByEmail(utenteLoggato.getEmail()) != null)) {
			return true;
		} else {
			this.utenteLoggato = null;
			return false;
		}
	}

	public void logout() {
		this.utenteLoggato = null;
	}

	/**
	 * Metodo per bloccare l'accesso agli utenti loggati che vogliono visitare la pagina login.xhtml
	 */
	public void checkIsLogged() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.utenteLoggato != null) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();

			nav.performNavigation("home");
		}
	}

}