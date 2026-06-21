package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteDTOList;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.shared.mapper.DateMapper;

@Component
public class DemandeVisiteMapper {
    private final BienImmobilierMapper bienImmobilierMapper;
    public DemandeVisiteMapper(DateMapper dateMapper, BienImmobilierMapper bienImmobilierMapper) {
        this.dateMapper = dateMapper;
        this.bienImmobilierMapper = bienImmobilierMapper;
    }
    private DateMapper dateMapper ;
    public DemandeVisiteCreateResponseDTO toDto(DemandeVisite demandeVisite) {
           
    
           return  DemandeVisiteCreateResponseDTO.builder()
            .id(demandeVisite.getId())
            .id_Locataire(demandeVisite.getLocataire().getId())
            .id_Bien(demandeVisite.getBienImmobilier().getId())
            .dateCreation(dateMapper.formatLocalDateTime(demandeVisite.getDateCreation(), "yyyy-MM-dd HH:mm:ss"))
            .statut(demandeVisite.getStatut().toString())
            .build();
      

}
    public  DemandeVisiteDTOList toDtoList(DemandeVisite demandeVisite) {
           
    
           return  DemandeVisiteDTOList.builder()
            .id(demandeVisite.getId())
            .id_Locataire(demandeVisite.getLocataire().getId())
            .nomLocataire(demandeVisite.getLocataire().getNom())
            .prenomLocataire(demandeVisite.getLocataire().getPrenom())
            .telephoneLocataire(demandeVisite.getLocataire().getTelephone())
            .dateCreation(dateMapper.formatLocalDateTime(demandeVisite.getDateCreation(), "yyyy-MM-dd HH:mm:ss"))
            .statut(demandeVisite.getStatut().toString())
            .bien(demandeVisite.getBienImmobilier()!=null ? bienImmobilierMapper.toDTO(demandeVisite.getBienImmobilier()) : null)
          
            .build();
      

}

}
