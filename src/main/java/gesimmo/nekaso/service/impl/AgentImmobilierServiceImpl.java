package gesimmo.nekaso.service.impl;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmoCreateDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmoCreateRequestDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmocreateResponseDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentListDto;
import gesimmo.nekaso.entity.AgentImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.exception.EntityExistException;
import gesimmo.nekaso.repository.AgentImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.repository.GestionnaireRepository;
import gesimmo.nekaso.service.AgentImmobilierService;

@Service
public class AgentImmobilierServiceImpl implements AgentImmobilierService {

    
    private final AgentImmobilierRepository agentImmobilierRepository;
    private final GestionnaireRepository gestionnaireRepository;
//    private final DemandeVisiteRepository demandeVisiteRepository;

    public AgentImmobilierServiceImpl(AgentImmobilierRepository agentImmobilierRepository, GestionnaireRepository gestionnaireRepository) {
        this.agentImmobilierRepository = agentImmobilierRepository;
        this.gestionnaireRepository = gestionnaireRepository;
    }

@Override
public AgentImmocreateResponseDto createAgentImmo(AgentImmoCreateRequestDto agentImmoCreateRequest,long id_Gestionnaire) {
    

    agentImmobilierRepository.findByTelephone(agentImmoCreateRequest.telephone())
            .ifPresent(agent -> {
                throw new EntityExistException("Un agent immobilier avec ce numéro existe déjà.");
            });
           
           
    Gestionnaire gestionnaire = gestionnaireRepository.findById(id_Gestionnaire)
            .orElseThrow(() -> new RuntimeException("Gestionnaire avec ID " + id_Gestionnaire + " non trouvé"));    
    AgentImmobilier agentImmobilier = new AgentImmobilier();
    agentImmobilier.setNom(agentImmoCreateRequest.nom());
    agentImmobilier.setPrenom(agentImmoCreateRequest.prenom());
    agentImmobilier.setTelephone(agentImmoCreateRequest.telephone());
    
    agentImmobilier.setGestionnaire(gestionnaire);

    AgentImmobilier saved = agentImmobilierRepository.save(agentImmobilier); 

    return new AgentImmocreateResponseDto(
        saved.getNom(),
        saved.getId(),
        saved.getPrenom(),
        saved.getTelephone()
    );
}
    public Page<AgentImmobilier> getAllAgents(Pageable pageable,Long idGestionnaire){
        Page<AgentImmobilier> agentsPage = agentImmobilierRepository.findByGestionnaire_Id(idGestionnaire, pageable);
        return agentsPage;
    }

    }

//     public AgentImmobilierServiceImpl(AgentImmobilierRepository agentImmobilierRepository, DemandeVisiteRepository demandeVisiteRepository) {
//         this.agentImmobilierRepository = agentImmobilierRepository;
//         this.demandeVisiteRepository = demandeVisiteRepository;
//     }

//     @Override
//     public DemandeVisite assignerDemandeVisite(Long idDemande, Long idAgent) {

        
//        AgentImmobilier agentImmobilier = agentImmobilierRepository.findById(idAgent)
//                 .orElseThrow(() -> new RuntimeException("Agent immobilier avec ID " + idAgent + " non trouvé"));

//         DemandeVisite demandeVisite = demandeVisiteRepository.findById(idDemande)
//                 .orElseThrow(() -> new RuntimeException("Demande de visite avec ID " + idDemande + " non trouvée"));
//         demandeVisite.setAgent(agentImmobilier);
//         return demandeVisiteRepository.save(demandeVisite);
       
//     }


    

