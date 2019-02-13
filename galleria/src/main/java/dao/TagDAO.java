package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import database.Tag;

@Named
@SessionScoped
public class TagDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Tag u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public void update(Tag u) {

		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Tag> findAll() {
		return em.createQuery("from Tag p", Tag.class).getResultList();
	}
}
