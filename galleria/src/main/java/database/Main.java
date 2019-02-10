package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	// EntityFactor per la gestione delle instanze con il database
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("database-galleria");
	private static EntityManager em = emf.createEntityManager();
	
	public static void tester() {
		List<String> nomi = new ArrayList<String>();
		List<String> cognomi = new ArrayList<String>();
		List<String> foto = new ArrayList<String>();
		Scanner s;
		try {
			s = new Scanner(new File("src/main/resources/nomi.txt"));
			while (s.hasNext()){
				nomi.add(s.next());
			}
			s.close();
			s = new Scanner(new File("src/main/resources/cognomi.txt"));
			while (s.hasNext()){
				cognomi.add(s.next());
			}
			s.close();
			s = new Scanner(new File("src/main/resources/foto.txt"));
			while (s.hasNextLine()){
				foto.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.shuffle(nomi);
		Collections.shuffle(cognomi);
		Collections.shuffle(foto);
		/*for(int i=0; i<nomi.size(); i++) {
			String nome = nomi.get(i).toLowerCase()+"."+cognomi.get(i%cognomi.size()).toLowerCase()+"@email.com";
			String password = nomi.get(i).toLowerCase()+cognomi.get(i%cognomi.size())+nomi.size()+cognomi.size();
			add(new Utente(nome,password,null));
		}*/
		for(int i=0; i<foto.size(); i++) {		
			String[] wow = (foto.get(i).toString()).split("&");
			String url = wow[0];
			String descrizione = wow[1];
			int endIndex=((255<descrizione.length())?255:descrizione.length());
			Double dimensione = (new Random()).nextDouble()*500;
			Short altezza = (short) (new Random()).nextInt(Short.MAX_VALUE + 1);
			Short larghezza = (short) (new Random()).nextInt(Short.MAX_VALUE + 1);
			add(new Fotografia(url,dimensione,altezza,larghezza,null,descrizione.substring(0, endIndex)));
		}
	}

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
		tester();
		/*Utente u = new Utente("Giorno", "Giovanna", null);
		System.out.println("Aggiungo " + u);
		add(u);
		u.setPermessi((short)99);
		System.out.println("Aggiorno " + u);
		if((update(u))==null){
			System.out.println("Aggiornamento fallito");
		}
		else {
			System.out.println("Aggiornamento con successo");
		}*/
		em.close();
		emf.close();
	}

}
