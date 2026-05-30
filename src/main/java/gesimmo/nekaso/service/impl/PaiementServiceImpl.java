package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.dto.QuittanceDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.PaiementRepository;
import gesimmo.nekaso.repository.QuittanceRepository;
import gesimmo.nekaso.service.PaiementService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final QuittanceRepository quittanceRepository;
    private final ContratBailRepository contratBailRepository;
    private final BienImmobilierRepository bienImmobilierRepository;
    private final DemandeLocationRepository demandeLocationRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Paiement creerPaiement(PaiementDTO dto) {
        LocalDate datePaiement = dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDate.now();
        String mois = datePaiement.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        Paiement paiement = Paiement.builder()
                .montant(dto.getMontant())
                .typePaiement(dto.getTypePaiement())
                .statut(dto.getStatut())
                .datePaiement(datePaiement)
                .mois(mois)
                .description(dto.getDescription())
                .cheminPDF(dto.getCheminPDF())
                .build();

        if (dto.getContratId() != null) {
            ContratBail contrat = contratBailRepository.findById(dto.getContratId())
                    .orElseThrow(() -> new IllegalArgumentException("Contrat introuvable"));
            paiement.setContrat(contrat);
            DemandeLocation demande = contrat.getDemandeLocation();
            if (demande != null) {
                paiement.setLocataire(demande.getLocataire());
                paiement.setBien(demande.getBien());
                paiement.setDemandeLocation(demande);
            }
        }

        if (dto.getBienId() != null) {
            BienImmobilier bien = bienImmobilierRepository.findById(dto.getBienId())
                    .orElseThrow(() -> new IllegalArgumentException("Bien introuvable"));
            paiement.setBien(bien);
        }

        if (dto.getDemandeLocationId() != null) {
            DemandeLocation demande = demandeLocationRepository.findById(dto.getDemandeLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Demande de location introuvable"));
            paiement.setDemandeLocation(demande);
            paiement.setLocataire(demande.getLocataire());
            paiement.setBien(demande.getBien());
        }

        if (dto.getLocataireId() != null) {
            Locataire locataire = entityManager.find(Locataire.class, dto.getLocataireId());
            if (locataire == null) {
                throw new IllegalArgumentException("Locataire introuvable");
            }
            paiement.setLocataire(locataire);
        }

        if (paiement.getContrat() == null
                && paiement.getLocataire() == null
                && paiement.getBien() == null
                && paiement.getDemandeLocation() == null) {
            throw new IllegalArgumentException(
                    "Un paiement doit être associé à un contrat, locataire, bien ou demande de location.");
        }

        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> rechercherPaiements(Long gestionnaireId,
            Long bienId,
            Long locataireId,
            LocalDate dateDebut,
            LocalDate dateFin,
            String statut,
            String mois,
            String typePaiement) {
        Specification<Paiement> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (gestionnaireId != null) {
                jakarta.persistence.criteria.Join<Object, Object> bienJoin = root.join("bien");
                jakarta.persistence.criteria.Join<Object, Object> gestionnaireJoin = bienJoin.join("gestionnaire");
                predicates.add(cb.equal(gestionnaireJoin.get("id"), gestionnaireId));
            }
            if (bienId != null) {
                predicates.add(cb.equal(root.get("bien").get("id"), bienId));
            }
            if (locataireId != null) {
                predicates.add(cb.equal(root.get("locataire").get("id"), locataireId));
            }
            if (statut != null && !statut.isBlank()) {
                predicates.add(cb.equal(root.get("statut"), statut));
            }
            if (typePaiement != null && !typePaiement.isBlank()) {
                predicates.add(cb.equal(root.get("typePaiement"), typePaiement));
            }
            if (mois != null && !mois.isBlank()) {
                predicates.add(cb.equal(root.get("mois"), mois));
            }
            if (dateDebut != null && dateFin != null) {
                predicates.add(cb.between(root.get("datePaiement"), dateDebut, dateFin));
            } else if (dateDebut != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("datePaiement"), dateDebut));
            } else if (dateFin != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("datePaiement"), dateFin));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
        return paiementRepository.findAll(spec);
    }

    @Override
    @Transactional
    public Quittance creerQuittance(Long paiementId, QuittanceDTO dto) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new IllegalArgumentException("Paiement introuvable"));

        Optional<Quittance> existing = quittanceRepository.findByPaiement_Id(paiementId);
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

        return quittanceRepository.save(quittance);
    }

    @Override
    public Quittance getQuittanceParPaiement(Long paiementId) {
        return quittanceRepository.findByPaiement_Id(paiementId).orElse(null);
    }

    private String genererNumero(Long paiementId, LocalDate emissionDate) {
        return String.format("Q-%d-%s", paiementId, emissionDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
