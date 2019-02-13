package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import database.Album;

@Named
@SessionScoped
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
		return em.createQuery("from Album p", Album.class).getResultList();
	}
}
