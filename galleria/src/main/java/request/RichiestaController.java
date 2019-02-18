package request;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import controller.FotografiaController;
import dao.FotografiaDAO;
import dao.UtenteDAO;
import database.Fotografia;
import database.Utente;
import utility.Password;

@Named
@SessionScoped
public class RichiestaController implements Serializable {
	@Inject
	RichiestaUtente richiestaUtente; // richiesta dell'utente
	@Inject
	UtenteDAO dao; // classe per l'esecuzione di query nel database
	@Inject
	FotografiaDAO fdao;
	private Utente utenteLoggato;
	private String ricerca = "";

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

	public void setUtenteLoggato(Utente utenteLoggato) {
		this.utenteLoggato = utenteLoggato;
	}

	public boolean isAdmin() {
		if (!isLogged())
			return false;
		return this.utenteLoggato.getPermessi() == 99;
	}

	public Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public void setUtenteLoggato() {
		Utente tmp = dao.findByEmail(richiestaUtente.getEmail()); // controllo se nel database sono presenti le
																	// credenziali utente
		if (tmp != null) {
			try {
				if (Password.check(richiestaUtente.getPassword(), tmp.getPassword())) { // controllo che la password
																						// inserita sia corretta
					this.utenteLoggato = new Utente(tmp); // instanzio l'utente loggato a partire da quello trovato
															// dalla query precedente
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	 * Metodo per bloccare l'accesso agli utenti loggati che vogliono visitare la
	 * pagina login.xhtml
	 */
	public void checkIsLogged() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.utenteLoggato != null) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();

			nav.performNavigation("home");
		}
	}

	// TODO sistemare l'aggiunta ai preferiti da galleria.xhtml, in particolare
	// dalla tabella dei risultati di ricerca. Il metodo non viene richiamato
	public void aggiungiPreferiti() {
		Integer foto_id = Integer.parseInt(this.ricerca);
		System.out.println("*************AGG PREF: " + this.utenteLoggato.getEmail() + "," + foto_id);
		dao.aggiungiPreferiti(this.utenteLoggato.getEmail(), foto_id);
	}

	// TODO fixare la rimozione (non funziona nei datatable ottenuti dalle ricerche
	// e nella home)
	public void eliminaPreferito(Integer foto_id) {
		System.out.println("***********************************ELIMINA PREFERITO***************************");
		dao.eliminaPreferito(utenteLoggato.getEmail(), fdao.find(foto_id));
	}

	public Set<Fotografia> getPreferiti() {
		Set<Fotografia> result = dao.getPreferiti(utenteLoggato.getEmail());
		return result;
	}

	public List<Fotografia> getFotografie() {
		return fdao.findAll();
	}

	public void elimina(Integer id) {
		fdao.elimina(id);
	}

	public Fotografia getById() {
		if ("".equals(this.ricerca) || this.ricerca == null)
			return null;
		Integer foto_id = Integer.parseInt(this.ricerca);
		System.out.println("**********" + foto_id + "***********");
		Fotografia f = fdao.find(foto_id); // TODO controllare che la ricerca sia un
											// numero
		System.out.println(f);
		return f;
	}

}