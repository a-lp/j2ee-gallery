package DAO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import database.Utente;

@Stateless
@Named
public class UtenteDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Object u) {
		try {
			em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	public Object update(Object u) {
		Object res = null;
		try {
			res = em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	public List<Utente> findAll() {
		return em.createQuery("from Utente p", Utente.class).getResultList();
	}
}
