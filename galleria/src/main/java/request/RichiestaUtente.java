package request;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * 
 * Classe per la gestione delle richieste da parte dell'utente. I valori gestiti
 * sono email, password e query di ricerca.
 */
@Named
@RequestScoped
public class RichiestaUtente implements Serializable {
	private String email;
	private String password;
	private String ricerca;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

}
