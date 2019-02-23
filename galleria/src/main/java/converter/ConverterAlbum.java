package converter;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;

import dao.AlbumDAO;
import database.Album;
import dto.AlbumDTO;

public class ConverterAlbum {
	@Inject
	AlbumDAO dao;
	@Inject
	private ConverterFotografia converterFoto;

	public AlbumDTO convertToDto(Album album) {
		if (album == null)
			return new AlbumDTO();
		try {
			AlbumDTO albumDTO = new AlbumDTO();
			BeanUtils.copyProperties(albumDTO, album);
			albumDTO.setFotografie(dao.getFotografie(album).stream().map(p -> converterFoto.convertToDto(p))
					.collect(Collectors.toSet()));
			return albumDTO;
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
