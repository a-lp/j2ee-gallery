package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.TagDAO;
import database.Tag;

/**
 * Classe di controllo per l'oggetto Tag.
 * 
 * @author Armando La Placa
 *
 */

@Named
@SessionScoped
public class TagController implements Serializable {
	@Inject
	TagDAO dao;

	/**
	 * Ricerca nel database dell'oggetto Tag passato a parametro.
	 * 
	 * @param tag Oggetto Tag che si vuole ricercare.
	 * @return Oggetto Tag trovato o null.
	 */
	public Tag findTag(Tag tag) {
		return dao.find(tag);
	}

	/**
	 * Metodo per la memorizzazione di un oggetto Tag passato a parametro.
	 * 
	 * @param tag Oggetto che si vuole memorizzare.
	 */
	public void save(Tag tag) {
		if (dao.find(tag) == null)
			dao.add(tag);
	}

	/**
	 * Metodo che restituisce tutte le categorie memorizzate nel database.
	 * 
	 * @return List<Tag> lista delle categorie presenti nel database.
	 */
	public List<Tag> getTags() {
		return dao.getAllTag();
	}
}
