package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.dto.QuittanceDTO;
import gesimmo.nekaso.dto.QuittanceAffichageDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.entity.enums.MethodePaiement;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.PaiementRepository;
import gesimmo.nekaso.repository.QuittanceRepository;
import gesimmo.nekaso.service.PaiementService;
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

    @Override
    @Transactional
    public Paiement creerPaiement(PaiementDTO dto) {
        LocalDate datePaiement = dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDate.now();
        String mois = datePaiement.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        Paiement paiement = Paiement.builder()
                .montant(dto.getMontant())
                .methodePaiement(dto.getModePaiement())
                .datePaiement(datePaiement)
                .mois(mois)
                .statut(dto.getStatut())
                .description(dto.getDescription())
                .cheminPDF(dto.getCheminPDF())
                .reference(genererReference(dto.getContratId(), datePaiement))
                .build();

        if (dto.getContratId() == null) {
            throw new IllegalArgumentException("Un paiement doit être associé à un contrat.");
        }

        ContratBail contrat = contratBailRepository.findById(dto.getContratId())
                .orElseThrow(() -> new IllegalArgumentException("Contrat introuvable"));
        paiement.setContrat(contrat);

        if (contrat.getDemandeLocation() != null) {
            paiement.setDemandeLocation(contrat.getDemandeLocation());
            paiement.setLocataire(contrat.getDemandeLocation().getLocataire());
            paiement.setBien(contrat.getDemandeLocation().getBien());
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
            MethodePaiement modePaiement) {
        Specification<Paiement> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (gestionnaireId != null) {
                jakarta.persistence.criteria.Join<Object, Object> contratJoin = root.join("contrat");
                jakarta.persistence.criteria.Join<Object, Object> demandeJoin = contratJoin.join("demandeLocation");
                jakarta.persistence.criteria.Join<Object, Object> bienJoin = demandeJoin.join("bien");
                jakarta.persistence.criteria.Join<Object, Object> gestionnaireJoin = bienJoin.join("gestionnaire");
                predicates.add(cb.equal(gestionnaireJoin.get("id"), gestionnaireId));
            }
            if (bienId != null) {
                predicates.add(cb.equal(root.get("contrat").get("demandeLocation").get("bien").get("id"), bienId));
            }
            if (locataireId != null) {
                predicates.add(cb.equal(root.get("contrat").get("demandeLocation").get("locataire").get("id"), locataireId));
            }
            if (statut != null && !statut.isBlank()) {
                predicates.add(cb.equal(root.get("statut"), statut));
            }
            if (modePaiement != null) {
                predicates.add(cb.equal(root.get("methodePaiement"), modePaiement.name()));
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

    @Override
    public List<QuittanceAffichageDTO> getQuittancesParLocataire(Long locataireId, Long bienId) {
        List<Quittance> quittances;
        if (bienId != null) {
            quittances = quittanceRepository.findByLocataireAndBien(locataireId, bienId);
        } else {
            quittances = quittanceRepository.findByLocataire(locataireId);
        }
        return quittances.stream().map(this::mapToAffichageDTO).toList();
    }

    @Override
    public List<QuittanceAffichageDTO> getQuittancesParBien(Long bienId, Long locataireId) {
        List<Quittance> quittances;
        if (locataireId != null) {
            quittances = quittanceRepository.findByBienAndLocataire(bienId, locataireId);
        } else {
            quittances = quittanceRepository.findByBien(bienId);
        }
        return quittances.stream().map(this::mapToAffichageDTO).toList();
    }

    private QuittanceAffichageDTO mapToAffichageDTO(Quittance quittance) {
        Paiement paiement = quittance.getPaiement();
        ContratBail contrat = paiement.getContrat();
        BienImmobilier bien = contrat != null && contrat.getDemandeLocation() != null
                ? contrat.getDemandeLocation().getBien()
                : null;

        return QuittanceAffichageDTO.builder()
                .id(quittance.getId())
                .numero(quittance.getNumero())
                .montantPaye(paiement.getMontant())
                .periode(paiement.getMois())
                .datePaiement(paiement.getDatePaiement())
                .dateEmission(quittance.getDateEmission())
                .cheminPDF(quittance.getCheminPDF())
                .bienId(bien != null ? bien.getId() : null)
                .bienAdresse(bien != null ? bien.getAdresse() : null)
                .bienType(bien != null ? bien.getTypeBien().toString() : null)
                .bienLoyer(bien != null ? bien.getLoyer() : null)
                .contratId(contrat != null ? contrat.getId() : null)
                .contratDateDebut(contrat != null ? contrat.getDateDebut() : null)
                .contratMontantLoyer(contrat != null ? contrat.getMontantLoyer() : null)
                .build();
    }

    private String genererNumero(Long paiementId, LocalDate emissionDate) {
        return String.format("Q-%d-%s", paiementId, emissionDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    private String genererReference(Long contratId, LocalDate datePaiement) {
        String prefix = contratId != null ? String.format("CTR-%d", contratId) : "CTR-NA";
        return String.format("%s-%s-%d", prefix, datePaiement.format(DateTimeFormatter.ofPattern("yyyyMMdd")), System.currentTimeMillis() % 1000000);
    }
}

