package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		//EntityFactor per la gestione delle instanze con il database
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("database-galleria");
		EntityManager em = emf.createEntityManager();
		System.out.println("icsdì");
		/*Utente u = new Utente(null,"Giorno","Giovanna");
		em.getTransaction().begin();
		em.persist(u);
		
		em.getTransaction().commit();*/
		em.close();
		emf.close();
		System.out.println("NANI?");
	}

}
