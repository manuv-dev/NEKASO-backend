package gesimmo.nekaso.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gesimmo.nekaso.dto.BienImmobilierDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;

public class BienImmobilierMapper {

	public BienImmobilierDTO toDTO(BienImmobilier bien) {
		if (bien == null) {
			return null;
		}

		BienImmobilierDTO dto = new BienImmobilierDTO();
		dto.setId(bien.getId());
		dto.setTypeBien(bien.getTypeBien() != null ? bien.getTypeBien().name() : null);
		dto.setAdresse(bien.getAdresse());
		dto.setSurface(bien.getSurface());
		dto.setNombrePieces(bien.getNombrePieces());
		dto.setLoyer(bien.getLoyer());
		dto.setStatutBien(bien.getStatutBien() != null ? bien.getStatutBien().name() : null);
		dto.setDescription(bien.getDescription());
		dto.setDateAjout(bien.getDateAjout());
		return dto;
	}

	public List<BienImmobilierDTO> toDTOList(List<BienImmobilier> biens) {
		List<BienImmobilierDTO> list = new ArrayList<>();
		for (BienImmobilier bien : biens) {
			list.add(toDTO(bien));
		}
		return list;
	}

	public BienImmobilier toEntity(BienImmobilierDTO dto) {
		BienImmobilier bien = new BienImmobilier();
		bien.setId(dto.getId());
		bien.setTypeBien(dto.getTypeBien() != null ? TypeBien.valueOf(dto.getTypeBien().trim().toUpperCase()) : null);
		bien.setAdresse(dto.getAdresse());
		bien.setSurface(dto.getSurface());
		bien.setNombrePieces(dto.getNombrePieces());
		bien.setLoyer(dto.getLoyer());
		bien.setStatutBien(dto.getStatutBien() != null ? Statut.valueOf(dto.getStatutBien().trim().toUpperCase()) : Statut.DISPONIBLE);
		bien.setDescription(dto.getDescription());
		bien.setDateAjout(dto.getDateAjout() != null ? dto.getDateAjout() : LocalDate.now());
		return bien;
	}
}
