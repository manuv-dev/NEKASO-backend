package gesimmo.nekaso.service.impl;


import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.exception.BienNonDisponibleException;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.mapper.DemandeLocationMapper;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.service.DemandeLocationService;


import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeLocationServiceImpl implements DemandeLocationService {

    private final DemandeLocationRepository demandeRepo;
    private  final DemandeLocationMapper demandeLocationMapper;
    private final ContratService contratService;
  
    private final LocataireRepository locataireRepository;
    private final ContratBailRepository contratBailRepository;
    private final BienImmobilierRepository bienRepository;

    public DemandeLocationServiceImpl(DemandeLocationRepository demandeRepo
            , ContratService contratService
            
            , LocataireRepository locataireRepository
            , ContratBailRepository contratBailRepository
            , BienImmobilierRepository bienRepository
            , DemandeLocationMapper demandeLocationMapper
            ) {
        this.demandeRepo = demandeRepo;
        this.contratService = contratService;
     
        this.locataireRepository = locataireRepository;
        this.contratBailRepository = contratBailRepository;
        this.bienRepository = bienRepository;
        this.demandeLocationMapper = demandeLocationMapper; 
    }

    @Override
    public DemandeLocationDTO createDemandeLocation(Long id_Locataire, Long id_Bien) {
        boolean existeDeja = demandeRepo.existsByLocataireIdAndBienImmobilierIdAndStatut(
            id_Locataire,
            id_Bien
        );
      
        
        if (existeDeja) {
            throw new RuntimeException("Vous avez déjà une demande pour ce bien.");
        }
            Locataire locataire = locataireRepository.findById(id_Locataire)
                    .orElseThrow(() -> new ResourceNotFoundException("Le locataire avec l'ID " + id_Locataire + " n'a pas été trouvé"));
            BienImmobilier bien = bienRepository.findById(id_Bien)
                    .orElseThrow(() -> new ResourceNotFoundException("Le bien immobilier avec l'ID " + id_Bien + " n'a pas été trouvé"));

            DemandeLocation demandelocation = DemandeLocation.builder()
                    .locataire(locataire)
                    .bien(bien)
                    .dateDemande(LocalDateTime.now())
                    .statut(StatutDemande.EN_ATTENTE)
                    .build();

            demandeRepo.save(demandelocation);

     

            return demandeLocationMapper.toDto(demandelocation);
    }
    @Override
    public DemandeLocationDTO changerStatutDemandeLocation(Long demandeId, String nouveauStatut) {
        DemandeLocation demande = demandeRepo.findById(demandeId)
                .orElseThrow(() -> new EntityNotFoundException("Demande de location non trouvée"));
        StatutDemande statutDem;
        try {
            statutDem = StatutDemande.valueOf(nouveauStatut.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide : " + nouveauStatut);
        }

        if (demande.getStatut() == null) {
            throw new BienNonDisponibleException("Le statut actuel de la demande est null, impossible de changer le statut.");
        }

        StatutDemande statutActuel = demande.getStatut();
        if (statutActuel == statutDem) {
            if (statutActuel == StatutDemande.REFUSEE) {
                throw new BienNonDisponibleException("La demande a déjà été refusée.");
            }
            if (statutActuel == StatutDemande.ACCEPTEE) {
                throw new BienNonDisponibleException("La demande a déjà été acceptée.");
            }
            if (statutActuel == StatutDemande.ANNULEE) {
                throw new BienNonDisponibleException("La demande a déjà été annulée.");
            }
            if (statutActuel == StatutDemande.EN_ATTENTE) {
                throw new BienNonDisponibleException("La demande est déjà en attente.");
            }
        }
        if (statutActuel == StatutDemande.EN_ATTENTE && statutDem == StatutDemande.REFUSEE) {
            demande.setStatut(statutDem);
            demandeRepo.save(demande);
        } 
        else if (statutActuel == StatutDemande.EN_ATTENTE && statutDem == StatutDemande.ACCEPTEE) {
            demande.setStatut(statutDem);
            demandeRepo.save(demande);
        } 
        else if ((statutActuel == StatutDemande.ACCEPTEE || statutActuel == StatutDemande.EN_ATTENTE) && statutDem == StatutDemande.ANNULEE) {
            demande.setStatut(statutDem);
            demandeRepo.save(demande);
        } 
        else {
            throw new BienNonDisponibleException("Changement de statut non autorisé. Le statut actuel est : " + statutActuel);
        }
        return demandeLocationMapper.toDto(demande);
    }

    // @Override
    // public DemandeLocationDTO changerStatutDemandeLocation(Long demandeId, String nouveauStatut) {
    //     DemandeLocation demande = demandeRepo.findById(demandeId)
    //             .orElseThrow(() -> new RuntimeException("Demande de location non trouvée"));
    //     // Validation du nouveau statut
    //     StatutDemande statutDem;
    //     try {
    //         statutDem = StatutDemande.valueOf(nouveauStatut.toUpperCase());
    //     } catch (IllegalArgumentException e) {
    //         throw new RuntimeException("Statut invalide : " + nouveauStatut);
    //     }
    //     if(demande.getStatut() == null){
    //         throw new RuntimeException("Le statut actuel de la demande est null, impossible de changer le statut.");
    //     }else if(demande.getStatut() == StatutDemande.EN_ATTENTE && statutDem == StatutDemande.REFUSEE){
    //         demande.setStatut(statutDem);
    //         demandeRepo.save(demande);
    //         throw new RuntimeException("La demande a été refusée avec succès.");
    //     } else if(demande.getStatut() == StatutDemande.EN_ATTENTE && statutDem == StatutDemande.ACCEPTEE){
    //         demande.setStatut(statutDem);
    //         demandeRepo.save(demande);
    //         throw new RuntimeException("La demande a été acceptée avec succès.");
    //     }else if(demande.getStatut() == StatutDemande.ACCEPTEE || demande.getStatut() == StatutDemande.EN_ATTENTE && statutDem == StatutDemande.ANNULEE){
    //         demande.setStatut(statutDem);
    //         demandeRepo.save(demande);
    //         throw new RuntimeException("La demande a été annulée avec succès.");
    //     }else {
    //         throw new RuntimeException("Changement de statut non autorisé. Le statut actuel est : " + demande.getStatut());
    //     }
        
    // }

        @Override
        public Page<DemandeLocation> getAllDemandesLocation(Pageable pageable, String statut, Long id_Locataire) {
            Page<DemandeLocation> demandesPage;
            if (statut == null || statut.isBlank()) {
                demandesPage = demandeRepo.findByLocataireId(id_Locataire, pageable);
            } else {
                demandesPage = demandeRepo.findByStatutAndLocataireId(
                        StatutDemande.valueOf(statut.toUpperCase()), id_Locataire, pageable
                );
            }
            return demandesPage;
        }

        @Deprecated
        public Page<DemandeLocation> getAllDemandesLocation(Pageable pageable, String statut) {
            Page<DemandeLocation> demandesPage;
            if (statut == null || statut.isBlank()) {
                demandesPage = demandeRepo.findAll(pageable);
            } else {
                demandesPage = demandeRepo.findAllByStatut(StatutDemande.valueOf(statut.toUpperCase()), pageable);
            }
            return demandesPage;
        }


}
