package DAO;

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

	public void add(Object u) {
		try {
			em.getTransaction().begin();
			em.persist(u); // controllo se � presente "u" nel database quindi aggiorno i suoi campi
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	public Object update(Object u) {
		Object res = null;
		try {
			em.getTransaction().begin();
			res = em.merge(u); // controllo se � presente "u" nel database quindi aggiorno i suoi campi
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	public List<Tag> findAll() {
		return em.createQuery("from Tag p", Tag.class).getResultList();
	}
}
