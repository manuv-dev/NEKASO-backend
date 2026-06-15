package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.dto.QuittanceDTO;
import gesimmo.nekaso.dto.QuittanceAffichageDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.MethodePaiement;
import gesimmo.nekaso.entity.enums.Mois;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.PaiementRepository;
import gesimmo.nekaso.repository.QuittanceRepository;
import gesimmo.nekaso.service.PaiementService;
import gesimmo.nekaso.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final QuittanceRepository quittanceRepository;
    private final ContratBailRepository contratBailRepository;
    private final PdfService pdfService;

    @Override
    @Transactional
    public Paiement creerPaiement(PaiementDTO dto) {
        ContratBail contrat = null;
        if (dto.getContratId() != null) {
            contrat = contratBailRepository.findById(dto.getContratId())
                    .orElseThrow(() -> new IllegalArgumentException("Contrat introuvable"));
        }

        LocalDate datePaiement = dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDate.now();
        Mois mois = parseMois(dto.getMois());
        MethodePaiement methodePaiement = parseMethodePaiement(dto.getMethodePaiement());

        Paiement paiement = Paiement.builder()
                .montant(dto.getMontant())
                .datePaiement(datePaiement)
                .mois(mois)
                .reference(dto.getReference())
                .methodePaiement(methodePaiement)
                .contrat(contrat)
                .build();

        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> rechercherPaiements(Long gestionnaireId, Long bienId, Long locataireId,
            LocalDate dateDebut, LocalDate dateFin, String statut, String mois, String typePaiement) {
        return paiementRepository.findAll().stream()
                .filter(paiement -> filterByLocataireAndContrat(paiement, locataireId, bienId))
                .filter(paiement -> filterByDateRange(paiement, dateDebut, dateFin))
                .filter(paiement -> filterByMois(paiement, mois))
                .filter(paiement -> filterByType(paiement, typePaiement))
                .toList();
    }

    @Override
    @Transactional
    public Quittance creerQuittance(Long paiementId, QuittanceDTO dto) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new IllegalArgumentException("Paiement introuvable"));

        Optional<Quittance> existing = quittanceRepository.findByPaiementId(paiementId);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Une quittance existe déjà pour ce paiement.");
        }

        LocalDate emissionDate = dto.getDateEmission() != null ? dto.getDateEmission() : LocalDate.now();
        String numero = dto.getNumero() != null ? dto.getNumero() : genererNumero(paiementId, emissionDate);

        Quittance quittance = Quittance.builder()
                .numero(numero)
                .dateEmission(emissionDate)
                .cheminPDF(dto.getCheminPDF())
                .montant(paiement.getMontant())
                .paiement(paiement)
                .build();

        Quittance savedQuittance = quittanceRepository.save(quittance);

        if (savedQuittance.getCheminPDF() == null || savedQuittance.getCheminPDF().isBlank()) {
            User locataireUser = resolveLocataireUser(paiement.getContrat());
            User gestionnaireUser = resolveGestionnaireUser(paiement.getContrat());
            String generatedPath = pdfService.genererQuittancePdf(savedQuittance, locataireUser, gestionnaireUser);
            savedQuittance.setCheminPDF(generatedPath);
            savedQuittance = quittanceRepository.save(savedQuittance);
        }

        return savedQuittance;
    }

    @Override
    public Quittance getQuittanceParPaiement(Long paiementId) {
        return quittanceRepository.findByPaiementId(paiementId).orElse(null);
    }

    @Override
    public List<QuittanceAffichageDTO> getQuittancesParLocataire(Long locataireId, Long bienId) {
        return List.of();
    }

    @Override
    public List<QuittanceAffichageDTO> getQuittancesParBien(Long bienId, Long locataireId) {
        return List.of();
    }

    private Mois parseMois(String mois) {
        if (mois == null || mois.isBlank()) {
            return Mois.Janvier;
        }

        String normalized = mois.trim().toLowerCase();
        return switch (normalized) {
            case "janvier" -> Mois.Janvier;
            case "fevrier", "février" -> Mois.Fevrier;
            case "mars" -> Mois.Mars;
            case "avril" -> Mois.Avril;
            case "mai" -> Mois.Mai;
            case "juin" -> Mois.Juin;
            case "juillet" -> Mois.Juillet;
            case "aout", "août" -> Mois.Aout;
            case "septembre" -> Mois.Septembre;
            case "octobre" -> Mois.Octobre;
            case "novembre" -> Mois.Novembre;
            case "decembre", "décembre" -> Mois.Decembre;
            default -> Mois.Janvier;
        };
    }

    private MethodePaiement parseMethodePaiement(String methodePaiement) {
        if (methodePaiement == null || methodePaiement.isBlank()) {
            return MethodePaiement.OM;
        }
        try {
            return MethodePaiement.valueOf(methodePaiement.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return MethodePaiement.OM;
        }
    }

    private String genererNumero(Long paiementId, LocalDate emissionDate) {
        return String.format("Q-%d-%s", paiementId, emissionDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    private boolean filterByLocataireAndContrat(Paiement paiement, Long locataireId, Long bienId) {
        if (locataireId == null && bienId == null) {
            return true;
        }

        if (paiement.getContrat() == null || paiement.getContrat().getDemandeLocation() == null) {
            return false;
        }

        ContratBail contrat = paiement.getContrat();
        if (locataireId != null && (contrat.getDemandeLocation().getLocataire() == null
                || !locataireId.equals(contrat.getDemandeLocation().getLocataire().getId()))) {
            return false;
        }

        if (bienId != null && (contrat.getDemandeLocation().getBien() == null
                || !bienId.equals(contrat.getDemandeLocation().getBien().getId()))) {
            return false;
        }

        return true;
    }

    private boolean filterByDateRange(Paiement paiement, LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null && dateFin == null) {
            return true;
        }
        LocalDate datePaiement = paiement.getDatePaiement();
        if (dateDebut != null && (datePaiement == null || datePaiement.isBefore(dateDebut))) {
            return false;
        }
        if (dateFin != null && (datePaiement == null || datePaiement.isAfter(dateFin))) {
            return false;
        }
        return true;
    }

    private boolean filterByMois(Paiement paiement, String mois) {
        if (mois == null || mois.isBlank()) {
            return true;
        }
        return paiement.getMois() != null && paiement.getMois().name().equalsIgnoreCase(mois.trim());
    }

    private boolean filterByType(Paiement paiement, String typePaiement) {
        if (typePaiement == null || typePaiement.isBlank()) {
            return true;
        }
        return paiement.getMethodePaiement() != null
                && paiement.getMethodePaiement().name().equalsIgnoreCase(typePaiement.trim());
    }

    private User resolveLocataireUser(ContratBail contrat) {
        if (contrat == null || contrat.getDemandeLocation() == null
                || contrat.getDemandeLocation().getLocataire() == null) {
            return null;
        }
        Locataire locataire = contrat.getDemandeLocation().getLocataire();
        return locataire.getUser();
    }

    private User resolveGestionnaireUser(ContratBail contrat) {
        if (contrat == null || contrat.getDemandeLocation() == null || contrat.getDemandeLocation().getBien() == null) {
            return null;
        }
        BienImmobilier bien = contrat.getDemandeLocation().getBien();
        Gestionnaire gestionnaire = bien.getGestionnaire();
        return gestionnaire != null ? gestionnaire.getUser() : null;
    }
}
