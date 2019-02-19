package controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FotografiaDAO;
import dao.UtenteDAO;
import database.Fotografia;
import database.Tag;
import database.Utente;
import utility.Password;

@Named
@SessionScoped
public class SessionController implements Serializable {
	@Inject
	LoginController loginUtente;
	@Inject
	UtenteDAO dao;
	@Inject
	FotografiaDAO fdao;
	/**
	 * Utente loggato.
	 */
	private Utente utente;
	/**
	 * Query di ricerca.
	 */
	private String ricerca = "";
	private Tag tag;

	// ***************** SEZIONE METODI DI SERVIZIO *****************//

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

	public void setUtenteLoggato(Utente utente) {
		this.utente = utente;
	}

	public Utente getUtenteLoggato() {
		return utente;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	// ***************** SEZIONE LOGIN *****************//

	public boolean isAdmin() {
		if (!isLogged())
			return false;
		return this.utente.getPermessi() == 99;
	}

	public String login() {
		Utente tmp = dao.findByEmail(loginUtente.getEmail()); // controllo se nel database sono presenti le credenziali
		// utente
		if (tmp != null) {
			try {
				if (Password.check(loginUtente.getPassword(), tmp.getPassword())) { // controllo che la password
					// inserita sia corretta
					this.utente = tmp;
					return "home";
				}
				return "login";
			} catch (Exception e) {
				System.out.println(
						"Errore: setUtenteLoggato(), " + loginUtente.getEmail() + ", " + loginUtente.getPassword());
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
		if (this.utente != null && (dao.find(utente.getId()) != null)) {
			return true;
		} else {
			this.utente = null;
			return false;
		}
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
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.utente != null) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();

			nav.performNavigation("home");
		}
	}

	public String registrati() {
		if (!"".equals(loginUtente.getEmail()) && !"".equals(loginUtente.getPassword())
				&& (dao.findByEmail(loginUtente.getEmail()) == null)) {
			Utente u = new Utente();
			u.setEmail(loginUtente.getEmail());
			u.setPassword(loginUtente.getPassword());
			dao.add(u);
			login();
			return "home";
		}
		return "registrazione";
	}

	// ***************** SEZIONE UTENTE *****************//
	public Utente getByEmail() {
		if ("".equals(ricerca))
			return null;
		else {
			Utente u = dao.findByEmail(ricerca);
			return u;
		}
	}

	// ***************** SEZIONE FOTOGRAFIE *****************//

	public List<Fotografia> getFotografie() {
		return fdao.findAll();
	}

	// TODO gestire il cascade
	public void eliminaFoto(Integer foto_id) {
		fdao.elimina(foto_id);
	}

	public Fotografia getById() {
		if (this.ricerca == null || "".equals(this.ricerca))
			return null;
		try {
			Integer foto_id = Integer.parseInt(this.ricerca);
			Fotografia f = fdao.find(foto_id);
			return f;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("La query inserita non è di tipo numerico");
			return null;
		}
	}

	public List<Fotografia> getByTag() {
		if (this.tag == null || "".equals(this.tag.getTag()))
			return null;
		try {
			List<Fotografia> f = fdao.findByTag(this.tag);
			return f;
		} catch (NumberFormatException e) {
			System.out.println("La query inserita non è di tipo numerico");
			return null;
		}
	}

	// ***************** SEZIONE PREFERITI *****************//
	public void aggiungiPreferiti(Integer foto_id) {
		Utente u = dao.find(utente.getId());
		u.getPreferiti().add(fdao.find(foto_id));
		System.out.println("************" + foto_id + "\n********" + utente);
		dao.update(u);
		this.utente = u;
		this.ricerca = "";
	}

	public void eliminaPreferito(Integer foto_id) {
		Utente u = dao.find(utente.getId());
		u.getPreferiti().remove(fdao.find(foto_id));
		System.out.println("************" + foto_id + "\n********" + utente);
		dao.update(u);
		this.utente = u;
	}

	public Set<Fotografia> getPreferiti() {
		return dao.getPreferiti(utente);
	}

	public boolean isPreferito(Fotografia f) {
		return (dao.find(this.utente.getId()).getPreferiti().contains(f) || this.utente.getPreferiti().contains(f));
	}

}