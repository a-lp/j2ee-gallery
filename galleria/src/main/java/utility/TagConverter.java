package utility;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import dao.TagDAO;
import database.Tag;

@Named
public class TagConverter implements Converter<Tag> {
	@Inject
	TagDAO dao;

	@Override
	public Tag getAsObject(FacesContext context, UIComponent component, String value) {
		Integer id;
		try {
			id = Integer.parseInt(value);
			Tag r = dao.get(id);
			return r;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Tag value) {
		if (value != null) {
			return "" + value.getId();
		}
		return null;
	}

}
