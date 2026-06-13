package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;

import gesimmo.nekaso.entity.DemandeLocation;

import gesimmo.nekaso.shared.mapper.DateMapper;
import org.springframework.stereotype.Component;


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
            .dateDemande(dateMapper.formatLocalDateTime(demandeLocation.getDateDemande(), "yyyy-MM-dd HH:mm:ss"))
            .statut(demandeLocation.getStatut().toString())
            .build();

    }
    public DemandeLocationDTO toDtoList(DemandeLocation demandeLocation) {

           return  DemandeLocationDTO.builder()
            .id(demandeLocation.getId())
            .locataireId(demandeLocation.getLocataire().getId())
            .bienId(demandeLocation.getBien().getId())
            .dateDemande(dateMapper.formatLocalDateTime(demandeLocation.getDateDemande(), "yyyy-MM-dd HH:mm:ss"))
            .statut(demandeLocation.getStatut().toString())
            .build();
    }

}