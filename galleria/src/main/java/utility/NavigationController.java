package utility;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NavigationController implements Serializable {
	public String showPage(String page) {
		if (page == null || page.equals(""))
			return "home";
		else
			return page;
	}
}