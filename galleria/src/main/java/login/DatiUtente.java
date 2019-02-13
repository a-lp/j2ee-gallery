package login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class DatiUtente implements Serializable {
	private Credenziali utenteLoggato;

	public Credenziali getUtenteLoggato() {
		return utenteLoggato;
	}

	public void setUtenteLoggato(Credenziali utenteLoggato) {
		this.utenteLoggato = new Credenziali();
		this.utenteLoggato.setPassword(utenteLoggato.getPassword());
		this.utenteLoggato.setEmail(utenteLoggato.getEmail());
	}
	
	public  boolean isLogged()
	{
		return this.utenteLoggato!=null;
	}
	
	
	public void checkIsLogged(ComponentSystemEvent event){
		/*
		
		
		
		if (!"admin".equals(fc.getExternalContext().getSessionMap().get("role"))){
*/
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.utenteLoggato!=null)
		{
			ConfigurableNavigationHandler nav 
			   = (ConfigurableNavigationHandler) 
				fc.getApplication().getNavigationHandler();
			
			nav.performNavigation("home");
		}
				
	  }
	
	

}

