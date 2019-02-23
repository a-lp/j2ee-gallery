package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.FotografiaDAO;
import database.Fotografia;

@Path("fotografia")
@Stateless
public class FotografiaREST {
	@Inject
	FotografiaDAO dao;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Fotografia> findAll() {
		List<Fotografia> fotografie = dao.findAll();
		return fotografie;
	}
}
