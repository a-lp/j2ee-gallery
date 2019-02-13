package controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import dao.TagDAO;
import database.Tag;

public class TagController implements Serializable  {
	@Inject
	TagDAO dao;

	private Tag tag = new Tag();

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void save() {
		dao.add(tag);
		tag = new Tag();
	}

	public List<Tag> getTags() {
		return dao.findAll();
	}
}
