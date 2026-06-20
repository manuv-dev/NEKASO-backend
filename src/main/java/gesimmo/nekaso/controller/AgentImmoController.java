package gesimmo.nekaso.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmoCreateRequestDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmocreateResponseDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentListDto;

import gesimmo.nekaso.entity.AgentImmobilier;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.mapper.AgentImmoMapper;
import gesimmo.nekaso.service.AgentImmobilierService;
import gesimmo.nekaso.shared.Response.PageResponse;

@RestController
@RequestMapping("/api/agents")
public class AgentImmoController {
    private final AgentImmobilierService agentImmobilierService;
    public final AgentImmoMapper agentImmobilierMapper;

    public AgentImmoController(AgentImmobilierService agentImmobilierService, AgentImmoMapper agentImmobilierMapper) {
        this.agentImmobilierService = agentImmobilierService;
        this.agentImmobilierMapper = agentImmobilierMapper; 
    }

    @PostMapping("gestionnaire/create")
    public ResponseEntity<AgentImmocreateResponseDto> createAgentImmo(@RequestBody AgentImmoCreateRequestDto agentImmoCreateRequest,Authentication authentication) {
        	Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
			Long id_Gestionnaire = gestionnaire.getId();
            
        AgentImmocreateResponseDto response = agentImmobilierService.createAgentImmo(agentImmoCreateRequest,id_Gestionnaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
        @GetMapping("/gestionnaire/mes_agents")
        public ResponseEntity<PageResponse<AgentListDto>> getAllAgents(
                Authentication authentication,
                @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
                @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

            Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size);

            Page<AgentImmobilier> agentsPage = agentImmobilierService.getAllAgents(pageable, gestionnaire.getId());
            Page<AgentListDto> agentDto = agentsPage.map(agentImmobilierMapper::toDTO);

            return new ResponseEntity<>(PageResponse.fromPage(agentDto), HttpStatus.OK);
        }

        
    
}
