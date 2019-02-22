package controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
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
@ConversationScoped
public class RichiestaController implements Serializable {
	@Inject
	private Conversation conversation;

	private Utente utente = new Utente();
	private Album album = new Album();
	private Fotografia fotografia = new Fotografia();
	private Tag tag = new Tag();
	private String ricerca;

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.ricerca = ricerca;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.utente = utente;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.album = album;
	}

	public Fotografia getFotografia() {
		return fotografia;
	}

	public void setFotografia(Fotografia fotografia) {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.fotografia = fotografia;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.tag = tag;
	}

}
