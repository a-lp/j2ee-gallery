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

	public Set<Fotografia> getPreferiti(Utente utente) {
		if (utente == null)
			return null;
		Utente tmp = em.find(Utente.class, utente.getId());
		tmp.getPreferiti().size();
		return tmp.getPreferiti();
	}

	public Set<Album> getAlbum(Utente utente) {
		if (utente == null)
			return null;
		Utente tmp = em.find(Utente.class, utente.getId());
		tmp.getAlbum().size();
		return tmp.getAlbum();
	}
}
