package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import database.Album;
import database.Utente;

@Stateless
public class AlbumDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Album u) {
		em.persist(u);
	}

	public void update(Album u) {
		em.merge(u);
	}

	public List<Album> findAll() {
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
}
