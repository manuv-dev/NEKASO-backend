package gesimmo.nekaso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmoCreateRequestDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmocreateResponseDto;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.service.AgentImmobilierService;

@RestController
@RequestMapping("/api/agents")
public class AgentImmoController {
    private final AgentImmobilierService agentImmobilierService;

    public AgentImmoController(AgentImmobilierService agentImmobilierService) {
        this.agentImmobilierService = agentImmobilierService;
    }

    @PostMapping("gestionnaire/create")
    public ResponseEntity<AgentImmocreateResponseDto> createAgentImmo(@RequestBody AgentImmoCreateRequestDto agentImmoCreateRequest,Authentication authentication) {
        	Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
			Long id_Gestionnaire = gestionnaire.getId();
            
        AgentImmocreateResponseDto response = agentImmobilierService.createAgentImmo(agentImmoCreateRequest,id_Gestionnaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
}
