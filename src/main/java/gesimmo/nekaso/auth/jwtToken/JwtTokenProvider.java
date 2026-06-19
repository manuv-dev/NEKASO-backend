package gesimmo.nekaso.auth.jwtToken;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationMs}")
    private Long jwtExpirationMs;

    public String generateToken(UserDetails user) {
       String token = "" ;
       Date iat = new Date();//date de generation du token
       Date exp = new Date(iat.getTime() + jwtExpirationMs); // date d'expiration du token
       SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
       token = Jwts.builder().setSubject(user.getUsername())
       .setIssuedAt(iat)
       .setExpiration(exp)
       .signWith(key)
        .compact();
        return token;
    }

     public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Handle token validation exceptions (e.g., expired, invalid signature)
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }

}
