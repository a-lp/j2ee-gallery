package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import database.Album;

@Named
@SessionScoped
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
