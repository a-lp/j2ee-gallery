package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.Utente;
import database.Utente_;


@Stateless
@Named
public class UtenteDAO implements Serializable {
	@PersistenceContext
	EntityManager em;
	CriteriaBuilder cb = em.getCriteriaBuilder();

	public void add(Utente u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public void update(Utente u) {
		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Utente> findAll() {
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		q.from(Utente.class);
		return em.createQuery(q).getResultList();
	}
	
	public Utente findByUsername(String username) {
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		Root<Utente> root = q.from(Utente.class);
		q.where(cb.like(root.get(Utente_.username), username));
		return em.createQuery(q).getSingleResult();
	}
}
