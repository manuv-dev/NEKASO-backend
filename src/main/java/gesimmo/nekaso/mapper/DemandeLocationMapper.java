package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;
import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteDTOList;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.shared.mapper.DateMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class DemandeLocationMapper {
    private final BienImmobilierMapper bienImmobilierMapper;
    public DemandeLocationMapper(DateMapper dateMapper, BienImmobilierMapper bienImmobilierMapper) {
        this.dateMapper = dateMapper;
        this.bienImmobilierMapper = bienImmobilierMapper;
    }
    private DateMapper dateMapper ;
    public DemandeLocationDTO toDto(DemandeLocation demandeLocation) {


           return  DemandeLocationDTO.builder()
            .id(demandeLocation.getId())
            .locataireId(demandeLocation.getLocataire().getId())
            .bienId(demandeLocation.getBien().getId())
            .dateDemande(dateMapper.formatLocalDate(demandeLocation.getDateDemande(), "yyyy-MM-dd"))
            .statut(demandeLocation.getStatut().toString())
            .build();

    }
    public DemandeLocationDTO toDtoList(DemandeLocation demandeLocation) {

           return  DemandeLocationDTO.builder()
            .id(demandeLocation.getId())
            .locataireId(demandeLocation.getLocataire().getId())
            .bienId(demandeLocation.getBien().getId())
            .dateDemande(dateMapper.formatLocalDate(demandeLocation.getDateDemande(), "yyyy-MM-dd"))
            .statut(demandeLocation.getStatut().toString())
            .build();
    }

}