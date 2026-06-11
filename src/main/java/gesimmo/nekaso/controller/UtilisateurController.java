package gesimmo.nekaso.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.AuthRequestDTO;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody AuthRequestDTO request) {
        User user = utilisateurService.createUser(
                request.getNom(),
                request.getPrenom(),
                request.getTelephone(),
                request.getMotDePasse(),
                resolveRole(request.getRole(), Role.LOCATAIRE),
                "ACTIF");
        return ResponseEntity.status(HttpStatus.CREATED).body(buildUserPayload(user));
    }

    @PostMapping("/locataires")
    public ResponseEntity<Map<String, Object>> createLocataire(@RequestBody AuthRequestDTO request) {
        Locataire locataire = utilisateurService.createLocataire(
                request.getNom(),
                request.getPrenom(),
                request.getTelephone(),
                request.getMotDePasse());
        return ResponseEntity.status(HttpStatus.CREATED).body(buildLocatairePayload(locataire));
    }

    @PostMapping("/gestionnaires")
    public ResponseEntity<Map<String, Object>> createGestionnaire(@RequestBody AuthRequestDTO request) {
        Gestionnaire gestionnaire = utilisateurService.createGestionnaire(
                request.getNom(),
                request.getPrenom(),
                request.getTelephone(),
                request.getMotDePasse());
        return ResponseEntity.status(HttpStatus.CREATED).body(buildGestionnairePayload(gestionnaire));
    }

    private Role resolveRole(String value, Role defaultRole) {
        if (value == null || value.isBlank()) {
            return defaultRole;
        }
        return Role.valueOf(value.trim().toUpperCase());
    }

    private Map<String, Object> buildUserPayload(User user) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", user.getId());
        payload.put("nom", user.getNom());
        payload.put("prenom", user.getPrenom());
        payload.put("telephone", user.getTelephone());
        payload.put("role", user.getRole().name());
        payload.put("statut", user.getStatut());
        return payload;
    }

    private Map<String, Object> buildLocatairePayload(Locataire locataire) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", locataire.getId());
        payload.put("userId", locataire.getUser().getId());
        payload.put("nom", locataire.getUser().getNom());
        payload.put("prenom", locataire.getUser().getPrenom());
        payload.put("telephone", locataire.getUser().getTelephone());
        payload.put("role", locataire.getUser().getRole().name());
        return payload;
    }

    private Map<String, Object> buildGestionnairePayload(Gestionnaire gestionnaire) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", gestionnaire.getId());
        payload.put("numeroGestionnaire", gestionnaire.getNumeroGestionnaire());
        payload.put("userId", gestionnaire.getUser().getId());
        payload.put("nom", gestionnaire.getUser().getNom());
        payload.put("prenom", gestionnaire.getUser().getPrenom());
        payload.put("telephone", gestionnaire.getUser().getTelephone());
        payload.put("role", gestionnaire.getUser().getRole().name());
        return payload;
    }
}
