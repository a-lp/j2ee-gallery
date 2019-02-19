package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import database.Tag;

@Stateless
@Named
public class TagDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Tag u) {
		em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public Tag get(Integer id) {
		return em.find(Tag.class, id);
	}

	public void update(Tag u) {

		em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
	}

	public List<Tag> getAllTag() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> q = cb.createQuery(Tag.class);
		q.from(Tag.class);
		List<Tag> tags = em.createQuery(q).getResultList();
		return tags;
	}
}
