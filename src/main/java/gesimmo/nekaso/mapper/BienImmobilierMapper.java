package gesimmo.nekaso.mapper;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierCreateDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierForm;
import gesimmo.nekaso.entity.enums.TypeBien;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.PhotoBienDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOLoc;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.entity.Gestionnaire;	

import gesimmo.nekaso.shared.mapper.DateMapper;
@Component
public class BienImmobilierMapper {


	private final DateMapper dateMapper;
	public BienImmobilierMapper(DateMapper dateMapper) {
		this.dateMapper = dateMapper;
	}
	public BienImmobilierCreateDTO toCreateDTO(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

		List<String> urlsString = java.util.Collections.emptyList();
		if (bien.getPhotos() != null) {
			urlsString = bien.getPhotos().stream()
					.map(photo -> photo.getUrlPhoto()) // Récupère juste l'URL String
					.toList();
		}

		return BienImmobilierCreateDTO.builder()
				.typeBien(bien.getTypeBien() != null ? bien.getTypeBien().name() : null)
				.libelle(bien.getLibelle())
				.adresse(bien.getAdresse())
				.surface(bien.getSurface())
				.statutBien(bien.getStatutBien() != null ? bien.getStatutBien().name() : null)
				.nombrePieces(bien.getNombrePieces())
				.loyer(bien.getLoyer())
				.description(bien.getDescription())
				.gestionnaireId(bien.getGestionnaire() != null ? bien.getGestionnaire().getId() : null)
				.photos(urlsString)
				.build();
	}
	public BienImmobilierResponseDTOGes toDTO(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

		return BienImmobilierResponseDTOGes.builder()
				.id(bien.getId())
				.typeBien(bien.getTypeBien() != null ? bien.getTypeBien().name() : null)
				.libelle(bien.getLibelle())
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
	
		public BienImmobilier toEntity(BienImmobilierForm form) {
		if (form == null) {
			return null;
		}

		BienImmobilier bien = new BienImmobilier();
		
		if (form.getTypeBien() != null) {
			try {
				bien.setTypeBien(TypeBien.valueOf(form.getTypeBien().toUpperCase()));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Type de bien invalide : " + form.getTypeBien());
			}
		}

		bien.setLibelle(form.getLibelle());
		bien.setAdresse(form.getAdresse());
		bien.setSurface(form.getSurface());
		bien.setNombrePieces(form.getNombrePieces());
		bien.setLoyer(form.getLoyer());
		bien.setDescription(form.getDescription());

		return bien;
	}
	public BienImmobilierResponseDTOLoc toDTOLoc(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

		return BienImmobilierResponseDTOLoc.builder()
				.id(bien.getId())
				.typeBien(bien.getTypeBien() != null ? bien.getTypeBien().name() : null)
				.libelle(bien.getLibelle())
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