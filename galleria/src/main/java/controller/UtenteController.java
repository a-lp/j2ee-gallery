package controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlbumDAO;
import dao.UtenteDAO;
import database.Album;
import database.Fotografia;
import database.Utente;
import utility.Password;

/**
 * Classe per la gestione degli oggetti di tipo Utente.La classe è di tipo
 * SessionScoped e può essere richiamata dalle pagine xhtml.
 * 
 * @author Armando La Placa
 *
 */

@Named
@SessionScoped
public class UtenteController implements Serializable {
	@Inject
	UtenteDAO dao;
	@Inject
	AlbumDAO adao;
	@Inject
	SessionController sessione;
	@Inject
	RichiestaController richiestaController;
	@Inject
	AlbumController albumController;

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

	/**
	 * Metodo che restituisce tutti gli utenti registrati nel database.
	 * 
	 * @return List<Utente> Lista degli utenti registrati nel database, null se non
	 *         ve ne sono.
	 */
	public List<Utente> getUtenti() {
		List<Utente> result = dao.findAll();
		if (result.size() == 0)
			result = null;
		return result;
	}

	/**
	 * Metodo per la restituzione di un numero massimo di utenti.
	 * 
	 * @param number Numero di utenti che si vuole restituiti.
	 * @return List<Utente> Contiene gli utenti.
	 */
	public List<Utente> getNUtenti(int number) {
		List<Utente> result = dao.getNUtenti(number);
		if (result.size() == 0)
			result = null;
		return result;
	}

	/**
	 * Metodo per la ricerca e restituzione di un utente tramite email.
	 * 
	 * @param email Email dell'utente che si vuole ricercare.
	 * @return Oggetto Utente ricercato, null se non è presente nel DB.
	 */
	public Utente getByEmail(String email) {
		if (email == null && "".equals(email))
			return null;
		return dao.findByEmail(email);
	}

	/**
	 * Metodo per la ricerca e restituzione di un utente tramite id.
	 * 
	 * @param id Id dell'utente che si vuole ricercare.
	 * @return Oggetto Utente ricercato, null se non è presente nel DB.
	 */
	public Utente getById(Integer id) {
		if (id == null && id > 0)
			return null;
		return dao.find(id);
	}

	/**
	 * Controllo sull'unicità dell'email inserita dall'utente.
	 * 
	 * @param utente Oggetto Utente contenente l'email.
	 * @return true se l'email è unica, false altrimenti.
	 */
	public boolean isValoreUnico(Utente utente) {
		return dao.findByEmail(utente.getEmail()) == null;
	}

	/**
	 * Metodo che restituisce i permessi dell'utente loggato.
	 * 
	 * @return Oggetto Short indicante i permessi dell'utente (99: admin; 1:utente).
	 */
	public Short getPermessi() {
		return dao.getPermessi(sessione.getUtenteLoggato().getEmail());
	}

	/**
	 * Metodo per l'aggiunta di un oggetto Fotografia al set di preferiti
	 * dell'utente loggato.
	 * 
	 * @param foto Fotografia da aggiungere ai preferiti.
	 */
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

	/**
	 * Metodo per l'eliminazione di un oggetto Fotografia dai preferiti dell'utente
	 * loggato.
	 * 
	 * @param foto Oggetto Fotografia da rimuovere dai preferiti.
	 */
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

	/**
	 * Metodo che restituisce il set di fotografie preferiti dall'utente loggato.
	 * 
	 * @return Set<Fotografia> Set dei preferiti.
	 */
	public Set<Fotografia> getPreferiti() {
		return dao.getPreferiti(sessione.getUtenteLoggato());
	}

	/**
	 * Metodo per la verifica che l'oggetto Fotografia passato a parametro sia già
	 * presente tra i preferiti.
	 * 
	 * @param f Oggetto Fotografia da ricercare tra i preferiti.
	 * @return true se l'oggetto è presente tra il set di preferiti, false
	 *         altrimenti.
	 */
	public boolean isPreferito(Fotografia f) {
		return (getPreferiti().contains(f));
	}

	/**
	 * Metodo per la restituzione degli album a cui può accedere l'utente loggato.
	 * 
	 * @return Set<Album> Album che può visualizzare l'utente loggato.
	 */
	public Set<Album> getAlbum() {
		return dao.getAlbum(sessione.getUtenteLoggato());
	}

	/**
	 * Metodo per la verifica che l'utente passato a parametro abbia assegnato un
	 * album inserito da form.
	 * 
	 * @param utente Utente su cui si vuole verificare l'accesso ad un album.
	 * @return true se l'utente ha accesso, false altrimenti.
	 */
	public boolean isAssignedAlbum(Utente utente) {
		Album tmp = albumController.getAlbum();
		if (tmp != null) {
			return dao.getAlbum(utente).contains(tmp);
		}
		return false;
	}

	/**
	 * Metodo per l'assegnazione di un album inserito da form ad un utente passato a
	 * parametro.
	 * 
	 * @param utente Utente a cui assegnare l'album.
	 */
	public void aggiungiAlbum(Utente utente) {
		Album tmp = albumController.getAlbum();
		utente.setAlbum(dao.getAlbum(utente));
		if (tmp != null) {
			utente.getAlbum().add(tmp);
			dao.update(utente);
		}
	}

	/**
	 * Metodo per la rimozione di un album tra quelli assegnati, inserito da form,
	 * all'utente passato a parametro.
	 * 
	 * @param utente Utente a cui rimuovere un album inserito da form.
	 */
	public void rimuoviAlbum(Utente utente) {
		Album tmp = albumController.getAlbum();
		utente.setAlbum(dao.getAlbum(utente));
		if (tmp != null) {
			utente.getAlbum().remove(tmp);
			dao.update(utente);
		}
	}

	/**
	 * Metodo per la registrazione di un utente nel database. Se questo non è
	 * presente, si registra un oggetto utente i cui campi sono compilati da form.
	 * Sulla password viene applicato un hashing.
	 * 
	 * @return Pagina home.xhtml se la registazione va a buon fine,
	 *         registrazione.xhtml altrimenti.
	 */
	public String registrati() {
		if (!"".equals(richiestaController.getUtente().getEmail())
				&& !"".equals(richiestaController.getUtente().getPassword())) {
			Utente u = new Utente();
			u.setEmail(richiestaController.getUtente().getEmail());
			try {
				u.setPassword(Password.getSaltedHash(richiestaController.getUtente().getPassword()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			save(u);
			return "home";
		}
		return "registrazione";
	}
}
