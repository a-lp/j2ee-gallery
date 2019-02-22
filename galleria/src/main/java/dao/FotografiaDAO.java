package dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import database.Fotografia;
import database.Tag;

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
		Fotografia foto = em.find(Fotografia.class, id);
		foto.getCategorie().clear();
		em.remove(foto);
	}

	/**
	 * Metodo per la ricerca di una fotografia con parametro id.
	 * 
	 * @param Integer
	 * @return Fotografia con id uguale al parametro o null.
	 */
	public Fotografia find(Integer id) {
		return em.find(Fotografia.class, id);
	}

	// TODO rifattorizzare
	public List<Fotografia> findByTag(Tag tag) {
		List<Fotografia> fotografie = findAll();
		/*
		 * if (fotografie != null) { for (Fotografia foto : fotografie) { if
		 * (!foto.getCategorie().contains(tag)) fotografie.remove(foto); } }
		 */
		Iterator<Fotografia> iter = fotografie.iterator();

		while (iter.hasNext()) {
			Fotografia str = iter.next();

			if (!str.getCategorie().contains(tag))
				iter.remove();
		}
		System.out.println(fotografie);
		return fotografie;
	}

	public List<Fotografia> findMax(int n) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Fotografia> q = cb.createQuery(Fotografia.class);
		q.from(Fotografia.class);
		return em.createQuery(q).setFirstResult(0).setMaxResults(n).getResultList();
	}

	public List<Fotografia> getBySearch(String ricerca) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);

		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Fotografia.class)
				.get();
		org.apache.lucene.search.Query query = qb.keyword().onFields("descrizione", "nome", "categorie.tag")
				.matching(ricerca).createQuery();
		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, Fotografia.class);
		List<Fotografia> result = persistenceQuery.getResultList();
		return result;
	}
}
