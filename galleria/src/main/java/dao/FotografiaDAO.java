package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import database.Fotografia;

@Named
@SessionScoped
public class FotografiaDAO implements Serializable {
	@PersistenceContext
	EntityManager em;
	CriteriaBuilder cb = em.getCriteriaBuilder();

	public void add(Fotografia u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public void update(Fotografia u) {
		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Fotografia> findAll() {
		CriteriaQuery<Fotografia> q = cb.createQuery(Fotografia.class);
		q.from(Fotografia.class);
		return em.createQuery(q).getResultList();
	}
}
