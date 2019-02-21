package utility;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AlbumDAO;
import database.Album;

@Named
public class AlbumConverter implements Converter<Album> {
	@Inject
	AlbumDAO dao;

	@Override
	public Album getAsObject(FacesContext context, UIComponent component, String value) {
		Integer id;
		try {
			id = Integer.parseInt(value);
			Album r = dao.get(id);
			return r;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Album value) {
		if (value != null) {
			return "" + value.getId();
		}
		return null;
	}

}
