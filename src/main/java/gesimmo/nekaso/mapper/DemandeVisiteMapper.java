package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.shared.mapper.DateMapper;

@Component
public class DemandeVisiteMapper {
    public DemandeVisiteMapper(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }
    private DateMapper dateMapper ;
    public DemandeVisiteCreateResponseDTO toDto(DemandeVisite demandeVisite) {
           
    
           return  DemandeVisiteCreateResponseDTO.builder()
            .id(demandeVisite.getId())
            .id_Locataire(demandeVisite.getLocataire().getId())
            .id_Bien(demandeVisite.getBienImmobilier().getId())
            .dateVisite(dateMapper.formatLocalDate(demandeVisite.getDateCreation(), "yyyy-MM-dd"))
            .statut(demandeVisite.getStatut().toString())
            .build();
      

}

}
