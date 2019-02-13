package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import database.Album;

@Stateless
public class AlbumDAO implements Serializable {
	@PersistenceContext
	EntityManager em;
	CriteriaBuilder cb = em.getCriteriaBuilder();

	public void add(Album u) {
		em.persist(u); 
	}

	public void update(Album u) {
		em.merge(u); 
	}

	public List<Album> findAll() {
		CriteriaQuery<Album> q = cb.createQuery(Album.class);
		q.from(Album.class);
		return em.createQuery(q).getResultList();
	}
}
