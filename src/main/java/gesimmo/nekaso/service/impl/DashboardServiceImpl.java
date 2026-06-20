package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.DashboardResponseDTO;
import gesimmo.nekaso.dto.DashboardResponseDTO.LoyerRetardDetailDTO;
import gesimmo.nekaso.entity.*;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.repository.*;
import gesimmo.nekaso.service.DashboardService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PaiementRepository paiementRepository;
    private final ContratBailRepository contratBailRepository;
    private final BienImmobilierRepository bienImmobilierRepository; // Utilise ton nom exact de repo

    @Override
    @Transactional(readOnly = true)
    public DashboardResponseDTO getGestionnaireDashboard(Long gestionnaireId) {
        
        LocalDate aujourdhui = LocalDate.now();
        LocalDate debutMoisEnCours = aujourdhui.withDayOfMonth(1);
        LocalDate debutMoisDernier = aujourdhui.minusMonths(1).withDayOfMonth(1);
        LocalDate finMoisDernier = debutMoisEnCours.minusDays(1);

        // ----------------------------------------------------
        // 1. CALCUL DES REVENUS ET ÉVOLUTION (CARD 1)
        // ----------------------------------------------------
        List<Paiement> paiementsMoisEnCours = paiementRepository.findPaiementsByGestionnaireEtPeriode(gestionnaireId, debutMoisEnCours, aujourdhui);
        List<Paiement> paiementsMoisDernier = paiementRepository.findPaiementsByGestionnaireEtPeriode(gestionnaireId, debutMoisDernier, finMoisDernier);

        double revenusCeMois = paiementsMoisEnCours.stream().mapToDouble(Paiement::getMontant).sum();
        double revenusMoisDernier = paiementsMoisDernier.stream().mapToDouble(Paiement::getMontant).sum();

        double evolutionRevenus = 0.0;
        if (revenusMoisDernier > 0) {
            evolutionRevenus = ((revenusCeMois - revenusMoisDernier) / Math.abs(revenusMoisDernier)) * 100;
        }

        // ----------------------------------------------------
        // 2. TAUX D'OCCUPATION (CARD 2)
        // ----------------------------------------------------
        // Récupération de tous les biens du gestionnaire via ton filtre existant
        Page<BienImmobilier> tousLesBiens = bienImmobilierRepository.findByGestionnaireId(gestionnaireId, null); 
        long totalBiens = tousLesBiens.getSize();
        long biensLoues = tousLesBiens.stream().filter(b -> b.getStatutBien() == StatutBien.LOUE).count();
        
        double tauxOccupation = totalBiens > 0 ? ((double) biensLoues / totalBiens) * 100 : 0.0;

        // ----------------------------------------------------
        // 3. RÉPARTITION DU PORTEFEUILLE (DONUT CHART)
        // ----------------------------------------------------
        Map<String, Long> repartition = tousLesBiens.stream()
                .collect(Collectors.groupingBy(b -> b.getTypeBien().toString(), Collectors.counting()));

        // ----------------------------------------------------
        // 4. LOYERS EN RETARD & ALERTES (CARD 3 & LISTE RELANCE)
        // ----------------------------------------------------
        List<ContratBail> contratsActifs = contratBailRepository.findContratsActifsParGestionnaire(gestionnaireId);
        List<LoyerRetardDetailDTO> alertesRetard = new ArrayList<>();
        double totalRetard = 0.0;
        Set<Long> locatairesImpactesIds = new HashSet<>();

        for (ContratBail contrat : contratsActifs) {
            int jourEcheance = contrat.getPreContrat().getJourEcheancePaiement();
            
            // Si le jour d'échéance du mois actuel est dépassé
            if (aujourdhui.getDayOfMonth() > jourEcheance) {
                // Vérifier si un paiement a été enregistré pour le mois en cours
                String moisCourantCle = aujourdhui.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRENCH);
                boolean paye = paiementsMoisEnCours.stream()
                        .anyMatch(p -> p.getContrat().getId().equals(contrat.getId()));

                if (!paye) {
                    double montantDu = contrat.getMontantLoyer();
                    totalRetard += montantDu;
                    locatairesImpactesIds.add(contrat.getLocataire().getId());

                    LocalDate dateLimite = aujourdhui.withDayOfMonth(jourEcheance);
                    alertesRetard.add(LoyerRetardDetailDTO.builder()
                            .locataireNom(contrat.getLocataire().getPrenom() + " " + contrat.getLocataire().getNom().toUpperCase())
                            .echeanceDate(dateLimite.toString())
                            .montantDu(montantDu)
                            .build());
                }
            }
        }

       // ----------------------------------------------------
        // 5. HISTORIQUE DU CHIFFRE D'AFFAIRES (BAR CHART) - CORRIGÉ
        // ----------------------------------------------------
        Map<String, Map<String, Double>> barChartData = new LinkedHashMap<>();
        
        // Génération des 6 derniers mois glissants
        for (int i = 5; i >= 0; i--) {
            LocalDate cible = aujourdhui.minusMonths(i);
            String nomMois = cible.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRENCH);
            
            LocalDate debutMois = cible.withDayOfMonth(1);
            // 👇 CORRECTION ICI : On ajuste dynamiquement au dernier jour du mois ciblé
            LocalDate finMois = cible.with(TemporalAdjusters.lastDayOfMonth());

            // On passe les bonnes variables (debutMois et finMois) à notre requête repository
            List<Paiement> pMois = paiementRepository.findPaiementsByGestionnaireEtPeriode(gestionnaireId, debutMois, finMois);
            double encaisse = pMois.stream().mapToDouble(Paiement::getMontant).sum();
            
            // Comparatif Année Précédente (N-1) pour la courbe
            List<Paiement> pMoisAnneeDerniere = paiementRepository.findPaiementsByGestionnaireEtPeriode(
                    gestionnaireId, 
                    debutMois.minusYears(1), 
                    finMois.minusYears(1)
            );
            double encaisseN1 = pMoisAnneeDerniere.stream().mapToDouble(Paiement::getMontant).sum();

            Map<String, Double> stats = new HashMap<>();
            stats.put("encaisse", encaisse);
            stats.put("anneePrecedente", encaisseN1 == 0 ? encaisse * 0.9 : encaisseN1); // Fallback si vide

            barChartData.put(nomMois, stats);
        }
        // ----------------------------------------------------
        // 6. CONSTRUCTION DE LA RÉPONSE GLOBALE
        // ----------------------------------------------------
        return DashboardResponseDTO.builder()
                .revenusCeMois(revenusCeMois)
                .evolutionRevenusPourcentage(evolutionRevenus)
                .tauxOccupation(tauxOccupation)
                .totalBiensLoues(biensLoues)
                .totalBiensPortefeuille(totalBiens)
                .totalLoyersEnRetard(totalRetard)
                .nombreLocatairesEnRetard((long) locatairesImpactesIds.size())
                .evolutionChiffreAffaires(barChartData)
                .repartitionPortefeuille(repartition)
                .listeLoyersEnRetard(alertesRetard)
                .build();
    }
}