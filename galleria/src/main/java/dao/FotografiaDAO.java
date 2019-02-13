package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import database.Fotografia;

@Named
@SessionScoped
public class FotografiaDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Fotografia u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public void update(Fotografia u) {
		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Fotografia> findAll() {
		return em.createQuery("from Fotografia p", Fotografia.class).getResultList();
	}
}
