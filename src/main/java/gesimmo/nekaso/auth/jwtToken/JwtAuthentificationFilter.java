package gesimmo.nekaso.auth.jwtToken;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;        

    public JwtAuthentificationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
      String header = request.getHeader("Authorization");
      if(header == null || !header.startsWith("Bearer ")) {
          filterChain.doFilter(request, response);
          return;
          // Validate the token and set the authentication in the security context
          // You can use your JwtTokenProvider to validate the token and extract user details
      }
      String token = header.substring(7); // Remove "Bearer " prefix
      // Validate the token and set the authentication in the security context
      if (!jwtTokenProvider.validateToken(token)) {
          filterChain.doFilter(request, response);
          return;
      }
      // You can use your JwtTokenProvider to validate the token and extract user details
      String userName=jwtTokenProvider.getUsernameFromToken(token); // Extract the username from the token using your JwtTokenProvider
      var userDetails = userDetailsService.loadUserByUsername(userName);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    }

    
    
}
