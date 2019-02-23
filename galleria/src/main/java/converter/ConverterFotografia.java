package converter;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import database.Fotografia;
import dto.FotografiaDTO;

public class ConverterFotografia {
	
	public FotografiaDTO convertToDto(Fotografia fotografia) {
		try {
			FotografiaDTO fotografiaDTO = new FotografiaDTO();
			BeanUtils.copyProperties(fotografiaDTO, fotografia);
			fotografiaDTO.setUrl("http://localhost:8080/galleria/foto/"+fotografiaDTO.getId());
			return fotografiaDTO;
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
