package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	// EntityFactor per la gestione delle instanze con il database
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("database-galleria");
	private static EntityManager em = emf.createEntityManager();

	//TODO: hibernate ricerca stile google Hibernate Search - Lucene. Utile per query
	
	public static void tester() {
		List<String> nomi = new ArrayList<String>();
		List<String> cognomi = new ArrayList<String>();
		List<String> foto = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		Scanner s;
		try {
			s = new Scanner(new File("src/main/resources/nomi.txt"));
			while (s.hasNext()) {
				nomi.add(s.next());
			}
			s.close();
			s = new Scanner(new File("src/main/resources/cognomi.txt"));
			while (s.hasNext()) {
				cognomi.add(s.next());
			}
			s.close();
			s = new Scanner(new File("src/main/resources/foto.txt"));
			while (s.hasNextLine()) {
				foto.add(s.nextLine());
			}
			s.close();
			s = new Scanner(new File("src/main/resources/tag.txt"));
			while (s.hasNextLine()) {
				String tmp = s.nextLine();
				tags.add(tmp);
				add(new Tag(tmp));
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Collections.shuffle(nomi);
		Collections.shuffle(cognomi);
		Collections.shuffle(foto);

		/*
		 * for(int i=0; i<nomi.size(); i++) { String nome =
		 * nomi.get(i).toLowerCase()+"."+cognomi.get(i%cognomi.size()).toLowerCase()+
		 * "@email.com"; String password =
		 * nomi.get(i).toLowerCase()+cognomi.get(i%cognomi.size())+nomi.size()+cognomi.
		 * size(); add(new Utente(nome,password,null)); }
		 */
		for (int i = 0; i < foto.size(); i++) {
			String[] wow = (foto.get(i).toString()).split("&");
			String url = wow[0];
			String descrizione = wow[1];
			int endIndex = ((255 < descrizione.length()) ? 255 : descrizione.length());
			Double dimensione = (new Random()).nextDouble() * 500;
			Short altezza = (short) (new Random()).nextInt(Short.MAX_VALUE + 1);
			Short larghezza = (short) (new Random()).nextInt(Short.MAX_VALUE + 1);
			Set<Tag> tag = new HashSet<Tag>();
			Collections.shuffle(tags);
			for (int j = 0; j < (new Random()).nextInt(5) + 1; j++) {
				System.out.println(tags.get(j));
				tag.add(new Tag(tags.get(j)));
			}
			List<Fotografia> query = em.createQuery("from Fotografia f where f.nome=:url",Fotografia.class)
					.setParameter("url", url)
					.getResultList();
			Fotografia tmp = query.get(0);
			tmp.setCategorie(new HashSet<Tag>());
			update(tmp);
		}
	}
	

	public static void add(Object u) {
		try {
			em.getTransaction().begin();
			em.persist(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	public static Object update(Object u) {
		Object res = null;
		try {
			em.getTransaction().begin();
			res = em.merge(u); // controllo se è presente "u" nel database quindi aggiorno i suoi campi
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return res;
		}
	}

	public static void main(String[] args) {
		System.out.println("Main");
		//tester();
		/*
		 * Utente u = new Utente("Giorno", "Giovanna", null);
		 * System.out.println("Aggiungo " + u); add(u); u.setPermessi((short)99);
		 * System.out.println("Aggiorno " + u); if((update(u))==null){
		 * System.out.println("Aggiornamento fallito"); } else {
		 * System.out.println("Aggiornamento con successo"); }
		 */
		//em.close();
		//emf.close();
	}

}
