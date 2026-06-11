package gesimmo.nekaso.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import gesimmo.nekaso.dto.AuthRequestDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void registerThenLogin_flow() throws Exception {
        String base = "http://localhost:" + port + "/api/auth";

        AuthRequestDTO register = new AuthRequestDTO();
        register.setTelephone("intuser");
        register.setMotDePasse("intpass");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> req = new HttpEntity<>(objectMapper.writeValueAsString(register), headers);
        ResponseEntity<String> regResp = restTemplate.postForEntity(base + "/register", req, String.class);

        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(regResp.getBody()).contains("User registered successfully");

        AuthRequestDTO login = new AuthRequestDTO();
        login.setTelephone("intuser");
        login.setMotDePasse("intpass");

        HttpEntity<String> loginReq = new HttpEntity<>(objectMapper.writeValueAsString(login), headers);
        ResponseEntity<String> loginResp = restTemplate.postForEntity(base + "/login", loginReq, String.class);

        assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResp.getBody()).contains("accessToken");
    }
}
