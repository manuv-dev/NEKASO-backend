package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.PreContratDTO.PreContratRequestDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratResponseDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratUpdateRequestDTO;
import gesimmo.nekaso.entity.*;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.entity.enums.StatutPreContrat;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.exception.BienNonDisponibleException;
import gesimmo.nekaso.mapper.PreContratMapper;
import gesimmo.nekaso.repository.*;
import gesimmo.nekaso.service.PreContratService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreContratServiceImpl implements PreContratService {

    private final PreContratRepository preContratRepository;
    private final DemandeLocationRepository demandeLocationRepository;
    private final DemandeVisiteRepository demandeVisiteRepository;
    private final BienImmobilierRepository bienImmobilierRepository;
    private final PreContratMapper preContratMapper;

        @Override
        @Transactional
        public PreContratResponseDTO createPreContrat(PreContratRequestDTO dto) {

            if (dto.getConditions() == null || dto.getConditions().trim().isEmpty()) {
                throw new IllegalArgumentException("Erreur : Les clauses et conditions particulières du pré-contrat ne peuvent pas être nulles ou vides.");
            }

            if (dto.getJourEcheancePaiement() == null || dto.getJourEcheancePaiement() <= 0 || dto.getJourEcheancePaiement() > 31) {
                throw new IllegalArgumentException("Erreur : Le jour d'échéance du paiement est invalide. Il doit être compris entre 1 et 31.");
            }

            if (dto.getDateDebutPrevu() == null) {
                throw new IllegalArgumentException("Erreur : La date de début prévue du bail ne peut pas être nulle.");
            }
            if (dto.getDateDebutPrevu().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Erreur : La date de début prévue (" + dto.getDateDebutPrevu() + ") ne peut pas être antérieure à la date du jour.");
            }

            if (dto.getDemandeLocationId() == null && dto.getDemandeVisiteId() == null) {
                throw new IllegalArgumentException("Erreur : Un pré-contrat doit obligatoirement être rattaché soit à une demande de location, soit à une demande de visite.");
            }
            if (dto.getDemandeLocationId() != null && dto.getDemandeVisiteId() != null) {
                throw new IllegalArgumentException("Erreur : Impossible de lier un pré-contrat simultanément à une location et une visite.");
            }

            PreContrat preContrat = preContratMapper.toEntity(dto);
            
            BienImmobilier bien = null;
            Locataire locataire = null;
            Gestionnaire gestionnaire = null; 

            if (dto.getDemandeLocationId() != null) {
                DemandeLocation dl = demandeLocationRepository.findById(dto.getDemandeLocationId())
                        .orElseThrow(() -> new EntityNotFoundException("Demande de location introuvable avec l'ID : " + dto.getDemandeLocationId()));
                
                if (dl.getStatut() != StatutDemande.ACCEPTEE) { 
                    throw new IllegalArgumentException("Action impossible : La demande de location doit être ACCEPTEE pour initier un pré-contrat. Statut actuel : " + dl.getStatut());
                }

                preContrat.setDemandeLocation(dl);
                bien = dl.getBien();
                locataire = dl.getLocataire();
                
                if (bien != null) {
                    gestionnaire = bien.getGestionnaire(); 
                }
                
            } else {
                DemandeVisite dv = demandeVisiteRepository.findById(dto.getDemandeVisiteId())
                        .orElseThrow(() -> new EntityNotFoundException("Demande de visite introuvable avec l'ID : " + dto.getDemandeVisiteId()));
                
                if (dv.getStatut() != VisiteStatut.CONFIRMEE) { 
                    throw new IllegalArgumentException("Action impossible : La demande de visite doit être CONFIRMEE pour initier un pré-contrat. Statut actuel : " + dv.getStatut());
                }

                preContrat.setDemandeVisite(dv);
                bien = dv.getBienImmobilier();
                locataire = dv.getLocataire();
                
                if (bien != null) {
                    gestionnaire = bien.getGestionnaire();
                }
            }

            if (bien == null) {
                throw new EntityNotFoundException("Erreur critique : Aucun bien immobilier n'est rattaché à cette demande.");
            }
            if (locataire == null) {
                throw new EntityNotFoundException("Erreur critique : Aucun locataire n'est trouvé dans cette demande.");
            }
            if (gestionnaire == null) {
                throw new EntityNotFoundException("Erreur critique : Le bien '" + bien.getLibelle() + "' n'est associé à aucun gestionnaire en base de données.");
            }

            if (bien.getStatutBien() != StatutBien.DISPONIBLE) {
                throw new BienNonDisponibleException("Action impossible : Le bien (" + bien.getLibelle() + ") n'est plus disponible. Statut actuel : " + bien.getStatutBien());
            }

            double montantLoyer = bien.getLoyer();
            double montantCaution = montantLoyer * 2;

            preContrat.setLocataire(locataire);
            preContrat.setMontantLoyer(montantLoyer);
            preContrat.setMontantCaution(montantCaution);

            bien.setStatutBien(StatutBien.RESERVE);
            bienImmobilierRepository.save(bien);

            PreContrat saved = preContratRepository.save(preContrat);
             DemandeVisite dv = demandeVisiteRepository.findById(dto.getDemandeVisiteId())
                .orElse(null);
                dv.setStatut(VisiteStatut.TERMINEE);
                demandeVisiteRepository.save(dv);

            return preContratMapper.toResponseDTO(saved);
        }

    @Override
    @Transactional
    public Page<PreContratResponseDTO> getPreContratsByLocataire(Long locataireId, Pageable pageable) {
        if (locataireId == null) {
            throw new IllegalArgumentException("L'ID du locataire ne peut pas être nul.");
        }
        return preContratRepository.findByLocataireUserId(locataireId, pageable)
                .map(preContratMapper::toResponseDTO);
    }
    @Override
    @Transactional
    public Page<PreContratResponseDTO> getPreContratsByGestionnaire(Long gestionnaireId, Pageable pageable) {
        if (gestionnaireId == null) {
            throw new IllegalArgumentException("L'ID du gestionnaire ne peut pas être nul.");
        }
        
        return preContratRepository.findByGestionnaireId(gestionnaireId, pageable)
                .map(preContratMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public PreContratResponseDTO validerPreContrat(Long id) {
        PreContrat preContrat = preContratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Impossible de valider : Pré-contrat introuvable avec l'ID : " + id));

        if (preContrat.getStatutPreContrat() != StatutPreContrat.EN_ATTENTE) {
            throw new IllegalArgumentException("Action impossible : Seuls les pré-contrats 'EN_ATTENTE' peuvent être validés. Statut actuel : " + preContrat.getStatutPreContrat());
        }

        BienImmobilier bien = preContrat.getDemandeLocation() != null 
                ? preContrat.getDemandeLocation().getBien() 
                : preContrat.getDemandeVisite().getBienImmobilier();

        if (bien == null) {
            throw new EntityNotFoundException("Erreur d'intégrité : Aucun bien n'est rattaché à ce pré-contrat.");
        }

        bien.setStatutBien(StatutBien.LOUE);
        bienImmobilierRepository.save(bien);

        preContrat.setStatutPreContrat(StatutPreContrat.VALIDER); 

        return preContratMapper.toResponseDTO(preContratRepository.save(preContrat));
    }

    @Override
    @Transactional
    public PreContratResponseDTO invaliderPreContrat(Long id) {
        PreContrat preContrat = preContratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Impossible d'invalider : Pré-contrat introuvable avec l'ID : " + id));

        if (preContrat.getStatutPreContrat() != StatutPreContrat.EN_ATTENTE) {
            throw new IllegalArgumentException("Action impossible : Seuls les pré-contrats 'EN_ATTENTE' peuvent être invalidés. Statut actuel : " + preContrat.getStatutPreContrat());
        }

        preContrat.setStatutPreContrat(StatutPreContrat.INVALIDER);
         BienImmobilier bien = preContrat.getDemandeLocation() != null 
                ? preContrat.getDemandeLocation().getBien() 
                : preContrat.getDemandeVisite().getBienImmobilier();

        if (bien == null) {
            throw new EntityNotFoundException("Erreur d'intégrité : Aucun bien n'est rattaché à ce pré-contrat.");
        }
        bien.setStatutBien(StatutBien.DISPONIBLE);

        return preContratMapper.toResponseDTO(preContratRepository.save(preContrat));
    }

    // @Override
    // @Transactional
    // public PreContratResponseDTO updatePreContrat(Long id, PreContratUpdateRequestDTO dto) {

    //     PreContrat preContrat = preContratRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Impossible de modifier : Pré-contrat introuvable avec l'ID : " + id));

    //     StatutPreContrat statutActuel = preContrat.getStatutPreContrat();
    //     if (statutActuel != StatutPreContrat.EN_ATTENTE && statutActuel != StatutPreContrat.INVALIDER) {
    //         throw new IllegalArgumentException("Modification impossible : Seuls les pré-contrats 'EN_ATTENTE' ou 'INVALIDER' peuvent être modifiés. Statut actuel : " + statutActuel);
    //     }

    //     if (dto.getConditions() != null) {
    //         preContrat.setConditions(dto.getConditions());
    //     }
    //     if (dto.getJourEcheancePaiement() != null) {
    //         preContrat.setJourEcheancePaiement(dto.getJourEcheancePaiement());
    //     }
    //     if (dto.getDateDebutPrevu() != null) {
    //         preContrat.setDateDebutPrevu(dto.getDateDebutPrevu());
    //     }

    //     preContrat.setStatutPreContrat(StatutPreContrat.EN_ATTENTE);

    //     PreContrat saved = preContratRepository.save(preContrat);
    //     return preContratMapper.toResponseDTO(saved);
    // }
}