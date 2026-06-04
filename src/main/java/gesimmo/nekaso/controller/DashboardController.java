package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.DashboardDTO;
import gesimmo.nekaso.dto.RepartitionBienDTO;
import gesimmo.nekaso.dto.RevenueMensuelDTO;
import gesimmo.nekaso.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/gestionnaire/{id}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getDashboardGestionnaire(id));
    }

    @GetMapping("/gestionnaire/{id}/revenus")
    public ResponseEntity<List<RevenueMensuelDTO>> getRevenus(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getRevenus6DerniersMois(id));
    }

    @GetMapping("/gestionnaire/{id}/repartition")
    public ResponseEntity<List<RepartitionBienDTO>> getRepartition(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getRepartitionBiens(id));
    }
}
