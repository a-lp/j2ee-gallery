package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlbumDAO;
import database.Album;
import database.Fotografia;

/**
 * Classe per la gestione degli oggetti di tipo Album. La classe è di tipo
 * SessionScoped e può essere richiamata dalle pagine xhtml.
 * 
 * @author Armando La Placa
 *
 */
@Named
@SessionScoped
public class AlbumController implements Serializable {
	@Inject
	AlbumDAO dao; // proxy per l'esecuzione delle query
	@Inject
	RichiestaController richiestaController; // proxy contenente i dati delle richieste degli utenti
	private Album album = new Album(); // oggetto di supporto

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	/**
	 * Metodo per la memorizzzione di un Album nel database. Prima di inserirlo si
	 * controlla che non vi sia un album con lo stesso nome già memorizzato.
	 */
	public void save() {
		setAlbum(richiestaController.getAlbum());
		if (dao.getByName(this.album.getNome()) == null) {
			dao.add(album);
		}
		this.album = new Album();
	}

	/**
	 * Metodo che restituisce la lista di tutti gli Album in DB.
	 * 
	 * @return List<Album> contentente gli Album nel DB.
	 */
	public List<Album> getAlbums() {
		return dao.getAllAlbum();
	}

	/**
	 * Metodo per l'inserimento di una foto nell'album di supporto alla classe. Una
	 * volta preso l'album attached dal DB (se presente) vi si inserisce l'oggetto
	 * fotografia passato a parametro e si aggiorna il DB.
	 * 
	 * @param foto Oggetto Fotografia da aggiungere.
	 */
	public void aggiungiFoto(Fotografia foto) {
		if (foto != null) {
			Album album = dao.get(this.album.getId());
			album.setFotografie(dao.getFotografie(album));
			if (album != null) {
				album.getFotografie().add(foto);
				dao.update(album);
			}
		}
	}

	/**
	 * Metodo per la rimozione di una foto dall'album di supporto alla classe. Una
	 * volta preso l'album attached dal DB (se presente) vi si rimuove l'oggetto
	 * fotografia passato a parametro e si aggiorna il DB.
	 * 
	 * @param foto Oggetto Fotografia da rimuovere.
	 */
	public void rimuoviFoto(Fotografia foto) {
		if (foto != null) {
			Album album = dao.get(this.album.getId());
			album.setFotografie(dao.getFotografie(album));
			if (album != null) {
				album.getFotografie().remove(foto);
				dao.update(album);
			}
		}
	}

	/**
	 * Metodo per la verifica della presenza di una fotografia in un album.
	 * 
	 * @param p Oggetto Fotografia da cercare nell'album.
	 * @return true se la fotografia è presente nell'album, false altrimenti.
	 */
	public boolean contains(Fotografia p) {
		Album album = dao.get(this.album.getId());
		album.setFotografie(dao.getFotografie(album));
		return album.getFotografie().contains(p);
	}

	/**
	 * Restituisce la lista delle foto presenti in un album.
	 * 
	 * @return List<Fotografia> dell'album.
	 */
	public List<Fotografia> getFotoByAlbum() {
		Album album = dao.get(this.album.getId());
		album.setFotografie(dao.getFotografie(album));
		System.out.println(album.getFotografie());
		return album.getFotografie();
	}
	
	public List<Fotografia> getFotoByAlbum(Album album) {
		album = dao.get(album.getId());
		album.setFotografie(dao.getFotografie(album));
		System.out.println(album.getFotografie());
		return album.getFotografie();
	}

	/**
	 * Metodo per aggiornare una entry della tabella Album nel DB con quella passata
	 * a parametro.
	 * 
	 * @param album2 Oggetto Album da aggiornare.
	 */
	public void update(Album album2) {
		dao.update(album2);
	}

	/**
	 * Metodo per la ricerca e restituzione dell'oggetto Album con id passato a
	 * parametro.
	 * 
	 * @param id Id dell'Album da ricercare nel DB.
	 * @return
	 */
	public Album get(Integer id) {
		return dao.get(id);
	}

}
