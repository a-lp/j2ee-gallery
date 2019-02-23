package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.Album;
import database.Fotografia;
import database.Utente;
import database.Utente_;

@Stateless
public class UtenteDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void elimina(String email) {
		em.remove(findByEmail(email));
	}

	public void add(Utente u) {
		em.persist(u);
	}

	public void update(Utente u) {
		em.merge(u);
	}

	public List<Utente> findAll() {
		EntityGraph<Utente> eg = (EntityGraph<Utente>) em.getEntityGraph("utenteLazy");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		q.from(Utente.class);
		return em.createQuery(q).setHint("javax.persistence.fetchgraph", eg).getResultList();
	}

	public Utente find(Integer id) {
		EntityGraph<Utente> eg = (EntityGraph<Utente>) em.getEntityGraph("utenteLazy");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		Root<Utente> root = q.from(Utente.class);
		q.where(cb.equal(root.get(Utente_.id), id));
		return em.createQuery(q).setHint("javax.persistence.fetchgraph", eg).getSingleResult();
	}

	public Utente findByEmail(String email) {
		try {
			EntityGraph<Utente> eg = (EntityGraph<Utente>) em.getEntityGraph("utenteLazy");
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
			Root<Utente> root = q.from(Utente.class);
			q.where(cb.like(root.get(Utente_.email), email));
			return em.createQuery(q).setHint("javax.persistence.fetchgraph", eg).getSingleResult();
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

	public Set<Fotografia> getPreferiti(Utente utente) {
		if (utente == null)
			return null;
		Utente tmp = find(utente.getId());
		return tmp.getPreferiti();
	}

	public Set<Album> getAlbum(Utente utente) {
		if (utente == null)
			return null;
		Utente tmp = find(utente.getId());
		return tmp.getAlbum();
	}

	public List<Utente> getNUtenti(int n) {
		EntityGraph<Utente> eg = (EntityGraph<Utente>) em.getEntityGraph("utenteLazy");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
		q.from(Utente.class);
		return em.createQuery(q).setHint("javax.persistence.fetchgraph", eg).setFirstResult(0).setMaxResults(n)
				.getResultList();
	}
}
