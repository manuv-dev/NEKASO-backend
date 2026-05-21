package gesimmo.nekaso.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UtilisateurDTO {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
}
