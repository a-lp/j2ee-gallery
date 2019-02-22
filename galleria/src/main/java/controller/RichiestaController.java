package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import database.Album;
import database.Fotografia;
import database.Tag;
import database.Utente;

/**
 * 
 * Classe per la gestione delle richieste da parte dell'utente.
 *
 */
@Named
@RequestScoped
public class RichiestaController implements Serializable {
	private Utente utente = new Utente();
	private Album album = new Album();
	private Fotografia fotografia = new Fotografia();
	private Tag tag = new Tag();
	private String ricerca;

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Fotografia getFotografia() {
		return fotografia;
	}

	public void setFotografia(Fotografia fotografia) {
		this.fotografia = fotografia;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
