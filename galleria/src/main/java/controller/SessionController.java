package controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlbumDAO;
import dao.FotografiaDAO;
import database.Album;
import database.Fotografia;
import database.Tag;
import database.Utente;
import utility.Password;

//TODO riorganizzare i metodi tra i vari controller 
@Named
@SessionScoped
public class SessionController implements Serializable {
	@Inject
	LoginController loginUtente;
	@Inject
	UtenteController utenteController;
	@Inject
	FotografiaDAO fdao;
	@Inject
	AlbumDAO adao;
	/**
	 * Utente loggato.
	 */
	private Utente utenteLoggato;
	/**
	 * Query di ricerca.
	 */
	private String ricerca = "";
	private Tag tag;
	private Album album;

	// ***************** SEZIONE METODI DI SERVIZIO *****************//

	public String getRicerca() {
		return ricerca;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

	public void setUtenteLoggato(Utente utenteLoggato) {
		this.utenteLoggato = utenteLoggato;
	}

	public Utente getUtenteLoggato() {
		return utenteLoggato;
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
		return this.utenteLoggato.getPermessi() == 99;
	}

	public String login() {
		Utente tmp = utenteController.getByEmail(loginUtente.getEmail()); // controllo se nel database sono presenti le
																			// credenziali
		// utenteLoggato
		if (tmp != null) {
			try {
				if (Password.check(loginUtente.getPassword(), tmp.getPassword())) { // controllo che la password
					// inserita sia corretta
					this.utenteLoggato = tmp;
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
	 * Metodo per la verifica che un utenteLoggato sia loggato.
	 * 
	 * @return true se l'utenteLoggato è loggato ed è presente nel database, false
	 *         altrimenti
	 */
	public boolean isLogged() {
		if (this.utenteLoggato != null && (utenteController.getById(utenteLoggato.getId()) != null)) {
			return true;
		} else {
			this.utenteLoggato = null;
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
		if (this.utenteLoggato != null) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();

			nav.performNavigation("home");
		}
	}

	public String registrati() {
		if (!"".equals(loginUtente.getEmail()) && !"".equals(loginUtente.getPassword())) {
			Utente u = new Utente();
			u.setEmail(loginUtente.getEmail());
			try {
				u.setPassword(Password.getSaltedHash(loginUtente.getPassword()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return utenteController.save(u);
		}
		return "registrazione";
	}

	// ***************** SEZIONE FOTOGRAFIE *****************//

	public List<Fotografia> getFotografie() {
		return fdao.findAll();
	}

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

	public List<Fotografia> getBySearch() {
		if (this.ricerca == null || "".equals(this.ricerca))
			return null;
		return fdao.getBySearch(this.ricerca);
	}

	public void aggiungiFoto(Fotografia foto) {
		System.out.println(this.album + " - " + foto);
		if (foto != null) {
			Album album = adao.get(this.getAlbum().getId());
			if (album != null) {
				album.getFotografie().add(foto);
				adao.update(album);
			} else
				System.out.println("Album vuoto: " + album);
		} else
			System.out.println("Foto vuoto: " + foto);
	}

}
