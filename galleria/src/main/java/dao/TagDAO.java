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

import database.Tag;
import database.Tag_;

@Stateless
@Named
public class TagDAO implements Serializable {
	@PersistenceContext
	EntityManager em;

	public void add(Tag u) {
		em.persist(u);
	}

	public Tag get(Integer id) {
		return em.find(Tag.class, id);
	}

	public void update(Tag u) {
		em.merge(u);
	}

	public List<Tag> getAllTag() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> q = cb.createQuery(Tag.class);
		q.from(Tag.class);
		List<Tag> tags = em.createQuery(q).getResultList();
		return tags;
	}

	public Tag find(Tag tag) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> q = cb.createQuery(Tag.class);
		Root<Tag> root = q.from(Tag.class);
		q.where(cb.equal(root.get(Tag_.tag), tag.getTag()));
		try {
			return em.createQuery(q).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
