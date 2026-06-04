// package gesimmo.nekaso.mapper;

// import java.util.ArrayList;
// import java.util.List;

// import gesimmo.nekaso.dto.DemandeVisiteDTO;
// import gesimmo.nekaso.dto.UtilisateurDTO;
// import gesimmo.nekaso.entity.DemandeVisite;

// public class DemandeVisiteMapper {

//     private final BienImmobilierMapper bienMapper = new BienImmobilierMapper();

//     public DemandeVisiteDTO toDTO(DemandeVisite entity) {
//         if (entity == null) {
//             return null;
//         }
//         DemandeVisiteDTO dto = new DemandeVisiteDTO();
//         dto.setId(entity.getId());
//         dto.setStatut(entity.getStatut() != null ? entity.getStatut().name() : null);
//         dto.setDateCreation(entity.getDateCreation() != null ? entity.getDateCreation().toLocalDate() : null);
//         if (entity.getLocataire() != null) {
//             UtilisateurDTO u = new UtilisateurDTO();
//             u.setId(entity.getLocataire().getId());
//             u.setNom(entity.getLocataire().getNom());
//             u.setPrenom(entity.getLocataire().getPrenom());
//             u.setTelephone(entity.getLocataire().getTelephone());
//             dto.setLocataire(u);
//         }
//         dto.setBien(bienMapper.toDTO(entity.getBienImmobilier()));
//         return dto;
//     }

//     public List<DemandeVisiteDTO> toDTOList(List<DemandeVisite> entities) {
//         List<DemandeVisiteDTO> list = new ArrayList<>();
//         if (entities == null) {
//             return list;
//         }
//         for (DemandeVisite e : entities) {
//             list.add(toDTO(e));
//         }
//         return list;
//     }
// }
 
