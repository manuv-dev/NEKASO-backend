package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.DashboardResponseDTO;

public interface DashboardService {
    DashboardResponseDTO getGestionnaireDashboard(Long gestionnaireId);
}