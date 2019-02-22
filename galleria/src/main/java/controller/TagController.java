package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.TagDAO;
import database.Tag;

@Named
@SessionScoped
public class TagController implements Serializable {
	@Inject
	TagDAO dao;

	public Tag findTag(Tag tag) {
		return dao.find(tag);
	}

	public void save(Tag tag) {
		if (dao.find(tag) == null)
			dao.add(tag);
	}

	public List<Tag> getTags() {
		return dao.getAllTag();
	}
}
