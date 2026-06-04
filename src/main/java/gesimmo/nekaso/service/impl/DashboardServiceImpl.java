package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.*;
import gesimmo.nekaso.repository.*;
import gesimmo.nekaso.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PaiementRepository paiementRepo;
    private final ContratBailRepository contratRepo;
    private final BienImmobilierRepository bienRepo;

    @Override
    public DashboardDTO getDashboardGestionnaire(Long gestionnaireId) {
        YearMonth moisActuel = YearMonth.now();
        YearMonth moisPrecedent = moisActuel.minusMonths(1);

        Double revenusMois = paiementRepo.sumMontantByGestionnaireAndMonth(gestionnaireId, moisActuel.toString());
        Double revenusMoisDernier = paiementRepo.sumMontantByGestionnaireAndMonth(gestionnaireId, moisPrecedent.toString());

        Double variation = (revenusMoisDernier != null && revenusMoisDernier > 0)
                ? ((revenusMois - revenusMoisDernier) / revenusMoisDernier) * 100
                : 0.0;

        int totalBiens = bienRepo.countByGestionnaireId(gestionnaireId);
        int biensLoues = contratRepo.countByGestionnaireIdAndActif(gestionnaireId, true);
        Double tauxOccupation = (totalBiens > 0) ? (biensLoues * 100.0 / totalBiens) : 0.0;

        Double loyersEnRetard = paiementRepo.sumMontantRetardByGestionnaire(gestionnaireId);
        int locatairesEnRetard = paiementRepo.countLocatairesRetardByGestionnaire(gestionnaireId);

        return DashboardDTO.builder()
                .revenusMois(revenusMois)
                .variationRevenus(variation)
                .tauxOccupation(tauxOccupation)
                .loyersEnRetard(loyersEnRetard)
                .locatairesEnRetard(locatairesEnRetard)
                .build();
    }

    @Override
    public List<RevenueMensuelDTO> getRevenus6DerniersMois(Long gestionnaireId) {
        List<RevenueMensuelDTO> result = new ArrayList<>();
        YearMonth moisActuel = YearMonth.now();

        for (int i = 0; i < 6; i++) {
            YearMonth mois = moisActuel.minusMonths(i);
            Double encaisse = paiementRepo.sumMontantByGestionnaireAndMonth(gestionnaireId, mois.toString());
            Double precedent = paiementRepo.sumMontantByGestionnaireAndMonth(gestionnaireId, mois.minusYears(1).toString());

            result.add(new RevenueMensuelDTO(mois.getMonth().toString(), encaisse, precedent));
        }
        return result;
    }

    @Override
    public List<RepartitionBienDTO> getRepartitionBiens(Long gestionnaireId) {
        List<Object[]> data = bienRepo.countByTypeBienAndGestionnaire(gestionnaireId);
        int total = bienRepo.countByGestionnaireId(gestionnaireId);

        List<RepartitionBienDTO> result = new ArrayList<>();
        for (Object[] row : data) {
            String typeBien = (String) row[0];
            Long nombre = (Long) row[1];
            Double pourcentage = (total > 0) ? (nombre * 100.0 / total) : 0.0;
            result.add(new RepartitionBienDTO(typeBien, nombre, pourcentage));
        }
        return result;
    }
}
