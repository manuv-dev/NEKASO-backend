package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.UtilisateurDTO;
import gesimmo.nekaso.entity.User;

@Component
public class UtilisateurMapper {


    public UtilisateurDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UtilisateurDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .telephone(user.getTelephone())
                .build();
    }
}
