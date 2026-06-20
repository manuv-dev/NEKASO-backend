package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.DashboardResponseDTO;
import gesimmo.nekaso.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Management", description = "Endpoints de statistiques et d'analyse pour les tableaux de bord")
@CrossOrigin(origins = "*") // Permet les appels cross-origin depuis ton front-end
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/gestionnaire/{gestionnaireId}")
    @Operation(summary = "Récupérer l'ensemble des données analytiques et KPIs du tableau de bord d'un gestionnaire")
    public ResponseEntity<DashboardResponseDTO> getGestionnaireDashboard(@PathVariable Long gestionnaireId) {
        return ResponseEntity.ok(dashboardService.getGestionnaireDashboard(gestionnaireId));
    }
}