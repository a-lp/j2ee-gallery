package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import database.Tag;
import database.Utente;

@Stateless
@Named
public class UtenteDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Utente u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public void update(Utente u) {
		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Utente> findAll() {
		return em.createQuery("from Utente p", Utente.class).getResultList();
	}
}
