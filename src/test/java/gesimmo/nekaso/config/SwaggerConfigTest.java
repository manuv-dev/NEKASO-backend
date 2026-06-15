package gesimmo.nekaso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void shouldExposeBearerSecurityScheme() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI.getComponents());
        SecurityScheme scheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertNotNull(scheme);
        assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
        assertEquals("bearer", scheme.getScheme());

        assertNotNull(openAPI.getSecurity());
    }
}
