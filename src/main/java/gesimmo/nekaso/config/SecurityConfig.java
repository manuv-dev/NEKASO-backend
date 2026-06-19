package gesimmo.nekaso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import gesimmo.nekaso.auth.jwtToken.JwtAuthentificationFilter;
import gesimmo.nekaso.auth.jwtToken.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth->auth
            .requestMatchers("/api/v1/auth/**").permitAll()
             .requestMatchers(
        "/h2-console/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/v3/api-docs.yaml",
        "/swagger-resources/**",
        "/webjars/**"
    ).permitAll()
            
            .anyRequest().authenticated())
            .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin()))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore( jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
     @Bean
    public JwtAuthentificationFilter jwtAuthenticationFilter() {
        return new JwtAuthentificationFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
      
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration Config) throws Exception {
        return Config.getAuthenticationManager();
    }
}
