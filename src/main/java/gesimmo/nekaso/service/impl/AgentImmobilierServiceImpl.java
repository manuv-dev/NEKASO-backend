package gesimmo.nekaso.service.impl;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.AgentImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.repository.AgentImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.service.AgentImmobilierService;

@Service
public class AgentImmobilierServiceImpl implements AgentImmobilierService {
//     private final AgentImmobilierRepository agentImmobilierRepository;
//    private final DemandeVisiteRepository demandeVisiteRepository;

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
    
}
