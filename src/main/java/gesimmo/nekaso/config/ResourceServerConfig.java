// package gesimmo.nekaso.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// @EnableWebSecurity
// public class ResourceServerConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/api/biens/**").hasRole("GESTIONNAIRE")
//                         .requestMatchers("/api/utilisateurs/**").hasRole("ADMIN")
//                         .anyRequest().authenticated()
//                 )
//                 .oauth2ResourceServer(oauth2 -> oauth2
//                         .jwt(
//                                 jwt -> jwt.jwkSetUri("http://localhost:8080/realms/nekaso/protocol/openid-connect/certs")
//                         )
//                 );
//         return http.build();
//     }
// }
