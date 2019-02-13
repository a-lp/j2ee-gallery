package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import database.Tag;

@Named
@SessionScoped
public class TagDAO implements Serializable {
	@PersistenceContext
	EntityManager em;
	CriteriaBuilder cb = em.getCriteriaBuilder();

	public void add(Tag u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public void update(Tag u) {

		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Tag> findAll() {
		CriteriaQuery<Tag> q = cb.createQuery(Tag.class);
		q.from(Tag.class);
		return em.createQuery(q).getResultList();
	}
}
