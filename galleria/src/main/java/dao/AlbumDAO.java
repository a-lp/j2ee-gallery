package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.Album;
import database.Album_;
import database.Fotografia;
import database.Utente;

@Stateless
@Named
public class AlbumDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Album u) {
		em.persist(u);
	}

	public void update(Album u) {
		em.merge(u);
	}

	public Album get(Integer id) {
		return em.find(Album.class, id);
	}

	public Album getByName(String nome) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Album> q = cb.createQuery(Album.class);
		Root<Album> root = q.from(Album.class);
		q.where(cb.equal(root.get(Album_.nome), nome));
		try {
			return em.createQuery(q).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Album> getAllAlbum() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Album> q = cb.createQuery(Album.class);
		q.from(Album.class);
		return em.createQuery(q).getResultList();
	}

	public Set<Album> getAlbum(Utente utente) {
		if (utente == null)
			return null;
		return em.find(Utente.class, utente.getId()).getAlbum();
	}

	public Set<Fotografia> getFotografie(Album album) {
		Album tmp = em.find(Album.class, album.getId());
		return tmp.getFotografie();
	}

}
