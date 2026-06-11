package gesimmo.nekaso.mapper;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.PhotoBienDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOLoc;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PhotoBien;

import gesimmo.nekaso.shared.mapper.DateMapper;
@Component
public class BienImmobilierMapper {


	private final DateMapper dateMapper;
	public BienImmobilierMapper(DateMapper dateMapper) {
		this.dateMapper = dateMapper;
	}

	public BienImmobilierResponseDTOGes toDTO(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

		return BienImmobilierResponseDTOGes.builder()
				.id(bien.getId())
				.typeBien(bien.getTypeBien() != null ? bien.getTypeBien().name() : null)
				.adresse(bien.getAdresse())
				.surface(bien.getSurface())
				.nombrePieces(bien.getNombrePieces())
				.loyer(bien.getLoyer())
				.statutBien(bien.getStatutBien() != null ? bien.getStatutBien().name() : null)
				.description(bien.getDescription())
				.dateAjout(dateMapper.formatLocalDate(bien.getDateAjout(), "dd/MM/yyyy"))
				.photos(photoListToDTO(bien.getPhotos()))
				.build();
	}
	public BienImmobilierResponseDTOLoc toDTOLoc(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

		return BienImmobilierResponseDTOLoc.builder()
				.id(bien.getId())
				.typeBien(bien.getTypeBien() != null ? bien.getTypeBien().name() : null)
				.adresse(bien.getAdresse())
				.loyer(bien.getLoyer())
				.statutBien(bien.getStatutBien() != null ? bien.getStatutBien().name() : null)

				.dateAjout(dateMapper.formatLocalDate(bien.getDateAjout(), "dd/MM/yyyy"))

				.build();
	}




	private List<PhotoBienDTO> photoListToDTO(List<PhotoBien> photos) {
		if (photos == null) {
			return new ArrayList<>();
		}
		List<PhotoBienDTO> dtos = new ArrayList<>();
		for (PhotoBien photo : photos) {
			dtos.add(PhotoBienDTO.builder()
					.id(photo.getId())
					.urlPhoto(photo.getUrlPhoto())
					.dateUpload(LocalDate.from(photo.getDateUpload()))
					.build()); 
		}
		return dtos;
	}
}