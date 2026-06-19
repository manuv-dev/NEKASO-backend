package gesimmo.nekaso.service.impl;


import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.exception.DemandeLocationException;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.mapper.DemandeLocationMapper;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.DemandeLocationService;


import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeLocationServiceImpl implements DemandeLocationService {

    private final DemandeLocationRepository demandeRepo;
    private  final DemandeLocationMapper demandeLocationMapper;
  
    private final LocataireRepository locataireRepository;
    private final BienImmobilierRepository bienRepository;

    public DemandeLocationServiceImpl(DemandeLocationRepository demandeRepo
            
            
            , LocataireRepository locataireRepository
            , BienImmobilierRepository bienRepository
            , DemandeLocationMapper demandeLocationMapper
            ) {
        this.demandeRepo = demandeRepo;
     
        this.locataireRepository = locataireRepository;
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
                throw new DemandeLocationException("Vous avez déjà une demande en attente pour ce bien.");
            }
            
            Locataire locataire = locataireRepository.findById(id_Locataire)
                    .orElseThrow(() -> new DemandeLocationException("Le locataire avec l'ID " + id_Locataire + " n'a pas été trouvé"));
                    
            BienImmobilier bien = bienRepository.findById(id_Bien)
                    .orElseThrow(() -> new DemandeLocationException("Le bien immobilier avec l'ID " + id_Bien + " n'a pas été trouvé"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Demande de location avec l'ID " + demandeId + " non trouvée"));

        StatutDemande statutDem;
        try {
            statutDem = StatutDemande.valueOf(nouveauStatut.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException("Le statut fourni n'existe pas : " + nouveauStatut);
        }

        StatutDemande statutActuel = demande.getStatut();
        if (statutActuel == null) {
            throw new DemandeLocationException("Le statut actuel de la demande est corrompu (null). Impossible de le modifier.");
        }

        if (statutActuel == statutDem) {
            throw new DemandeLocationException("La demande est déjà au statut : " + statutActuel);
        }

        boolean transitionAutorisee = false;

        if (statutActuel == StatutDemande.EN_ATTENTE) {

            transitionAutorisee = true;
        } else if (statutActuel == StatutDemande.ACCEPTEE && statutDem == StatutDemande.ANNULEE) {
            transitionAutorisee = true;
        }

        if (transitionAutorisee) {
            demande.setStatut(statutDem);
            demandeRepo.save(demande);
        } else {
            throw new DemandeLocationException("Changement de statut non autorisé. Impossible de passer de " + statutActuel + " à " + statutDem);
        }

        return demandeLocationMapper.toDto(demande);
    }

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

        @Override
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
