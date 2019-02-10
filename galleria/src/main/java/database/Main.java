package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	// EntityFactor per la gestione delle instanze con il database
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("database-galleria");
	private static EntityManager em = emf.createEntityManager();

	public static void add(Object u) {
		try {
			em.getTransaction().begin();
			em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public static Object update(Object u) {
		Object res=null;
		try {
			em.getTransaction().begin();
			res=em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			return res;
		}
	}

	public static void main(String[] args) {
		Utente u = new Utente("Giorno", "Giovanna", null);
		System.out.println("Aggiungo " + u);
		add(u);
		u.setPermessi((short)99);
		System.out.println("Aggiorno " + u);
		if((update(u))==null){
			System.out.println("Aggiornamento fallito");
		}
		else {
			System.out.println("Aggiornamento con successo");
		}
		em.close();
		emf.close();
	}

}
