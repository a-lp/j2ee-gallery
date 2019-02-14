package login;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class LoginController {
	@Inject
	Credenziali credenziali;
	@Inject
	DatiUtente datiUtente;

	public boolean controlloCredenziali() {
		return datiUtente.controlloCredenziali(credenziali);
	}
	
	public String login() {
		if(controlloCredenziali()) {
			datiUtente.setUtenteLoggato(credenziali);
			return "home";
		}
		else return "login";
	}
}