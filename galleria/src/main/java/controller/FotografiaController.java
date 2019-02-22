package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FotografiaDAO;
import database.Album;
import database.Fotografia;
import database.Tag;

/**
 * Classe per la gestione degli oggetti di tipo Fotografia.La classe è di tipo
 * SessionScoped e può essere richiamata dalle pagine xhtml.
 * 
 * @author Armando La Placa
 *
 */
@Named
@SessionScoped
public class FotografiaController implements Serializable {
	@Inject
	FotografiaDAO dao;
	@Inject
	TagController tagController; // proxy di supporto
	@Inject
	AlbumController albumController; // proxy di supporto
	@Inject
	RichiestaController richiestaController; // proxy contenente i dati delle richieste degli utenti
	private Fotografia fotografia = new Fotografia();

	public Fotografia getFotografia() {
		return fotografia;
	}

	public void setFotografia(Fotografia fotografia) {
		this.fotografia = fotografia;
	}

	/**
	 * Metodo per l'eliminazione dell'oggetto Fotografia con id passato a parametro.
	 * 
	 * @param id Id dell'oggetto da eliminare.
	 */
	public void elimina(Integer id) {
		this.dao.elimina(id);
	}

	/**
	 * Metodo che ricerca e restituisce un oggetto Fotografia con id richiesto
	 * dall'utente tramite form.
	 * 
	 * @exception NumberFormatException Se l'id inserito nella ricerca non è di tipo
	 *                                  Integer.
	 * @return Oggetto Fotografia con id ricercato, null se l'oggetto non è stato
	 *         trovato.
	 */
	public Fotografia getById() {
		if ("".equals(richiestaController.getRicerca()) || richiestaController.getRicerca() == null) {
			return null;
		}
		try {
			Fotografia f = dao.find(Integer.parseInt(richiestaController.getRicerca()));
			return f;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Metodo per il salvataggio di un oggetto Fotografia in DB. I dati vengono
	 * inseriti da form. Le categorie vengono ricercate nella tabella Tag del DB e,
	 * se non sono presenti, vengono inserite altrimenti vengono utilizzate quelle
	 * già nel DB.
	 */
	public void save() {
		Fotografia fotografia = new Fotografia();
		fotografia.setUrl(richiestaController.getFotografia().getUrl());
		if (dao.findByURL(fotografia.getUrl()) == null) {
			fotografia.setNome(richiestaController.getFotografia().getNome());
			fotografia.setDescrizione(richiestaController.getFotografia().getDescrizione());
			String[] tag = richiestaController.getRicerca().split(",");
			if (tag.length > 0) {
				for (String t : tag) {
					t = t.trim().toLowerCase();
					Tag tmp = new Tag(t);
					Tag result = tagController.findTag(tmp);
					if (result == null) { // se non trovo il tag, lo salvo in DB
						tagController.save(tmp);
						result = tagController.findTag(tmp);
					}
					fotografia.getCategorie().add(result);
				}
			}
			dao.add(fotografia);
			this.fotografia = new Fotografia();
		}
	}

	/**
	 * Metodo che restituisce tutte le fotografie presenti nel database.
	 * 
	 * @return List<Fotografia> contiene la lista di tutte le fotografie.
	 */
	public List<Fotografia> getFotografie() {
		return dao.findAll();
	}

	/**
	 * Restituisce un numero massimo di fotografie.
	 * 
	 * @param max Numero massimo di fotografie che si vuole restituite.
	 * @return List<Fotografia> lista delle fotografie.
	 */
	public List<Fotografia> getMaxFotografie(int max) {
		return dao.findMax(max);
	}

	/**
	 * Metodo per la ricerca delle fotografia tramite categoria inserita da form.
	 * 
	 * @return List<Fotografia> lista delle fotografie con tag corrispondente a
	 *         quello richiesto, null se non sono presenti foto con quella
	 *         categoria.
	 */
	public List<Fotografia> getByTag() {
		if (richiestaController.getTag() == null || "".equals(richiestaController.getTag().getTag()))
			return null;
		List<Fotografia> f = dao.findByTag(richiestaController.getTag());
		if (f.size() == 0)
			return null;
		return f;
	}

	/**
	 * Metodo per la ricerca delle fotografie. Si basa sull'utilizzo della libreria
	 * di ricerca Hibernate Search (Lucene), effettuata sui campi Nome, Descrizione
	 * e Categorie dell'oggetto Fotografia.
	 * 
	 * @return List<Fotografia> lista di oggetti Fotografia che corrispondono alla
	 *         query di ricerca, null se non vi sono fotografie che rispettano la
	 *         query.
	 */
	public List<Fotografia> getBySearch() {
		if (richiestaController.getRicerca() == null || "".equals(richiestaController.getRicerca()))
			return null;
		List<Fotografia> f = dao.getBySearch(richiestaController.getRicerca());
		if (f.size() == 0)
			return null;
		return f;
	}

	/**
	 * Metodo per l'aggiunta di oggetti Fotografia in un Album richiesto tramite
	 * form.
	 * 
	 * @param foto Oggetto Fotografia da aggiungere all'Album.
	 */
	public void aggiungiFoto(Fotografia foto) {
		if (foto != null) {
			Album album = albumController.get(richiestaController.getAlbum().getId());
			if (album != null) {
				album.getFotografie().add(foto);
				albumController.update(album);
			}
		}
	}

}
