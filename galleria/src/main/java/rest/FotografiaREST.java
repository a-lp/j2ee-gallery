package rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import converter.ConverterFotografia;
import dao.FotografiaDAO;
import dto.FotografiaDTO;

@Path("fotografia")
@Stateless
public class FotografiaREST {
	@Inject
	FotografiaDAO dao;
	@Inject
	private ConverterFotografia converterFotografia;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FotografiaDTO> findAll() {
		return dao.findAll().stream().map(p -> converterFotografia.convertToDto(p)).collect(Collectors.toList());
	}

	@GET
	@Path("{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FotografiaDTO> findByName(@PathParam("nome") String nome) {
		return dao.getBySearch(nome).stream().map(p -> converterFotografia.convertToDto(p)).collect(Collectors.toList());
	}
}
