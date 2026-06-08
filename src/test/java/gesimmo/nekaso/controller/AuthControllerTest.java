package gesimmo.nekaso.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import gesimmo.nekaso.dto.AuthRequestDTO;
import gesimmo.nekaso.dto.AuthResponseDTO;
import gesimmo.nekaso.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void loginShouldReturnJwtToken() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("user1");
        request.setPassword("secret");

        when(authService.login(any(AuthRequestDTO.class)))
                .thenReturn(new AuthResponseDTO("Bearer", "fake-jwt-token"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").value("fake-jwt-token"));
    }

    @Test
    void registerShouldCreateNewUser() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("newuser");
        request.setPassword("password");

        when(authService.register(any(AuthRequestDTO.class))).thenReturn("User registered successfully.");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."));
    }
}
