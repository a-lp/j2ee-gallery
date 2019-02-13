package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.Utente;
import database.Utente_;
import utility.Password;
@Stateless
public class UtenteDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Utente u) {
		try {
			u.setPassword(Password.getSaltedHash(u.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("add");
		}
		em.persist(u);
	}

	public void update(Utente u) {
		em.merge(u);
	}

	public List<Utente> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		q.from(Utente.class);
		return em.createQuery(q).getResultList();
	}

	public Utente findByEmail(String email) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
			Root<Utente> root = q.from(Utente.class);
			q.where(cb.like(root.get(Utente_.email), email));
			return em.createQuery(q).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
