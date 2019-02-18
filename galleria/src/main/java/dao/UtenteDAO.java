package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.Fotografia;
import database.Utente;
import database.Utente_;
import utility.Password;

@Stateless
public class UtenteDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void elimina(String email) {
		em.remove(findByEmail(email));
	}

	public void add(Utente u) {
		try {
			u.setPassword(Password.getSaltedHash(u.getPassword())); // calcolo hashing della password
		} catch (Exception e) {
			e.printStackTrace();
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

	public Utente find(Integer id) {
		return em.find(Utente.class, id);
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

	public Short getPermessi(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		Root<Utente> root = q.from(Utente.class);
		q.where(cb.like(root.get(Utente_.email), email));
		return em.createQuery(q).getSingleResult().getPermessi();
	}

	public void aggiungiPreferiti(Utente u, Fotografia f) {
		u.getPreferiti().add(f);
		update(u);
	}

	public Set<Fotografia> getPreferiti(Utente u) {
		if (u == null)
			return null;
		return em.find(Utente.class, u.getId()).getPreferiti();
	}

	public Set<Fotografia> getPreferiti(String email) {
		Utente u = findByEmail(email);
		if (u == null)
			return null;
		return u.getPreferiti();
	}

	public void eliminaPreferito(Utente u, Fotografia foto) {
		u.getPreferiti().remove(foto);
		update(u);
	}
}
