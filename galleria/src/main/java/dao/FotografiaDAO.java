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

import database.Fotografia;
import database.Fotografia_;

@Stateless
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Fotografia> q = cb.createQuery(Fotografia.class);
		q.from(Fotografia.class);
		return em.createQuery(q).getResultList();
	}

	public void elimina(Integer id) {
		System.out.println("********************************************************************");
		Fotografia foto = em.find(Fotografia.class, id);
		em.remove(foto);
	}

	public Fotografia find(Integer id) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Fotografia> q = cb.createQuery(Fotografia.class);
			Root<Fotografia> root = q.from(Fotografia.class);
			q.where(cb.equal(root.get(Fotografia_.id), id));
			return em.createQuery(q).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Fotografia> findMax(int n){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Fotografia> q = cb.createQuery(Fotografia.class);
		q.from(Fotografia.class);
		return em.createQuery(q).setFirstResult(0).setMaxResults(n).getResultList();
	}
}
