package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.DashboardResponseDTO;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/gestionnaire")
    public ResponseEntity<DashboardResponseDTO> getGestionnaireDashboard(Authentication authentication) {
        Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
        Long gestionnaireId = gestionnaire.getId();
        return ResponseEntity.ok(dashboardService.getGestionnaireDashboard(gestionnaireId));
    }
}