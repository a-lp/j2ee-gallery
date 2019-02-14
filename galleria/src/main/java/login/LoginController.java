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

	public String login() {
		datiUtente.setUtenteLoggato(credenziali);
		return "home";
	}
}