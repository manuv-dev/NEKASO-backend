package gesimmo.nekaso.mapper;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.PhotoBienDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTO;
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

	public BienImmobilierResponseDTO toDTO(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

	return BienImmobilierResponseDTO.builder()
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


	// public List<BienImmobilierResponseDTO> toDTOList(List<BienImmobilier> biens) {
	// 	List<BienImmobilierResponseDTO> list = new ArrayList<>();
	// 	for (BienImmobilier bien : biens) {
	// 		list.add(toDTO(bien));
	// 	}
	// 	return list;
	// }

	// public BienImmobilier toEntity(BienImmobilierResponseDTO dto) {
	// 	BienImmobilier bien = new BienImmobilier();
	// 	bien.setId(dto.getId());
	// 	bien.setTypeBien(dto.getTypeBien() != null ? TypeBien.valueOf(dto.getTypeBien().trim().toUpperCase()) : null);
	// 	bien.setAdresse(dto.getAdresse());
	// 	bien.setSurface(dto.getSurface());
	// 	bien.setNombrePieces(dto.getNombrePieces());
	// 	bien.setLoyer(dto.getLoyer());
	// 	bien.setStatutBien(dto.getStatutBien() != null ? Statut.valueOf(dto.getStatutBien().trim().toUpperCase()) : Statut.DISPONIBLE);
	// 	bien.setDescription(dto.getDescription());
	// 	bien.setDateAjout(dto.getDateAjout() != null ? dto.getDateAjout() : LocalDate.now());
	// 	return bien;
	// }

	private List<PhotoBienDTO> photoListToDTO(List<PhotoBien> photos) {
		if (photos == null) {
			return new ArrayList<>();
		}
		List<PhotoBienDTO> dtos = new ArrayList<>();
		for (PhotoBien photo : photos) {
			PhotoBienDTO dto = new PhotoBienDTO();
			dto.setUrlPhoto(photo.getUrlPhoto());
			dto.setDateUpload(photo.getDateUpload());
			dtos.add(dto);
		}
		return dtos;
	}
}
