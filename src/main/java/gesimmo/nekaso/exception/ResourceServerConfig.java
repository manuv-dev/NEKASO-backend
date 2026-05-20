//package gesimmo.nekaso.exception;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//@EnableWebSecurity
//@Configuration
//public class ResourceServerConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/biens/**").hasRole("GESTIONNAIRE")
//                        .requestMatchers("/api/utilisateurs/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwkSetUri("${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/certs"))
//                );
//        return http.build();
//    }
//}
