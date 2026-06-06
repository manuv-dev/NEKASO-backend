package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.DemandeVisiteResponseDTO;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.shared.mapper.DateMapper;

@Component
public class DemandeVisiteMapper {
private final DateMapper dateMapper;
private final UtilisateurMapper utilisateurMapper;
private final BienImmobilierMapper bienImmobilierMapper;
public DemandeVisiteMapper(DateMapper dateMapper, UtilisateurMapper utilisateurMapper, BienImmobilierMapper bienImmobilierMapper) {
		this.dateMapper = dateMapper;
		this.utilisateurMapper = utilisateurMapper;
		this.bienImmobilierMapper = bienImmobilierMapper;
	}

	public DemandeVisiteResponseDTO toDTO(DemandeVisite demande) {
		if (demande == null) {
			return null;
		}
		return DemandeVisiteResponseDTO.builder()
				.id(demande.getId())
				.statut(demande.getStatut() != null ? demande.getStatut().name() : null)
				.dateCreation(dateMapper.formatLocalDate(demande.getDateCreation(), "dd/MM/yyyy"))
				.locataire(demande.getLocataire() != null ? utilisateurMapper.toDTO(demande.getLocataire()) : null)
				.bien(demande.getBienImmobilier() != null ? bienImmobilierMapper.toDTOVisite(demande.getBienImmobilier()) : null)
				.build();
	}
}
 
