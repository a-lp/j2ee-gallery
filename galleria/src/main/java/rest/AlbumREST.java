package rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import converter.ConverterAlbum;
import dao.AlbumDAO;
import dto.AlbumDTO;

@Path("album")
@Stateless
public class AlbumREST {
	@Inject
	AlbumDAO dao;
	@Inject
	private ConverterAlbum converterAlbum;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AlbumDTO> findAll() {
		return dao.getAllAlbum().stream().map(p -> converterAlbum.convertToDto(p)).collect(Collectors.toList());
	}
}
