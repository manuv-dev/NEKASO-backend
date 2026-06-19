package gesimmo.nekaso.repository.mock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.entity.AgentImmobilier;
import gesimmo.nekaso.repository.AgentImmobilierRepository;
@Component
@Order(5) 
public class AgentImmbilierMock implements CommandLineRunner {

    private final AgentImmobilierRepository agentImmobilierRepository;

    public AgentImmbilierMock(AgentImmobilierRepository agentImmobilierRepository) {
        this.agentImmobilierRepository = agentImmobilierRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       if(agentImmobilierRepository.count() == 0) {
        var agent1 = new AgentImmobilier();
        agent1.setNom("Doe");
        agent1.setPrenom("Jane");
        agent1.setTelephone("0987654321");
        agentImmobilierRepository.save(agent1);
       }
    }
    
}
