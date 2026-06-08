package gesimmo.nekaso.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.UtilisateurDTO;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @GetMapping("/me")
    public ResponseEntity<UtilisateurDTO> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setUsername(authentication.getName());
        dto.setRoles(authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return ResponseEntity.ok(dto);
    }
}
