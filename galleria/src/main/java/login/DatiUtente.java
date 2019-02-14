package login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UtenteDAO;
import database.Utente;
import utility.Password;

@Named
@SessionScoped
public class DatiUtente implements Serializable {
	private Credenziali utenteLoggato;
	@Inject
	UtenteDAO dao;

	public Short getPermessi() {
		if (utenteLoggato.getEmail() != null) {
			return dao.getPermessi(utenteLoggato.getEmail());
		}
		return 99;
	}
	
	public boolean isAdmin() {
		if(utenteLoggato==null)
			return false;
		return getPermessi()==99;
	}

	public Credenziali getUtenteLoggato() {
		return utenteLoggato;
	}

	public void setUtenteLoggato(Credenziali utenteLoggato) {
		this.utenteLoggato = new Credenziali();
		this.utenteLoggato.setPassword(utenteLoggato.getPassword());
		this.utenteLoggato.setEmail(utenteLoggato.getEmail());
	}

	public boolean isLogged() {
		if(this.utenteLoggato!=null) {
			if(dao.findByEmail(this.utenteLoggato.getEmail())==null) {
				this.utenteLoggato=null;
				return false;
			}
			return true;
		}
		return false;
	}
	
	public String logout() {
		this.utenteLoggato=null;
		return "login";
	}

	public void checkIsLogged(ComponentSystemEvent event) {
		/*
		 * 
		 * 
		 * 
		 * if (!"admin".equals(fc.getExternalContext().getSessionMap().get("role"))){
		 */
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.utenteLoggato != null) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();

			nav.performNavigation("home");
		}

	}


	public boolean controlloCredenziali(Credenziali credenziali) {
		Utente user = dao.findByEmail(credenziali.getEmail());
		try {
			if(user!=null && Password.check(credenziali.getPassword(),user.getPassword()))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
