package is.hi.hbv501g.hbv501g_h3.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Autowired
    private UserRepository userRepository;

    // secret key (ensure it's at least 32 characters long for HS256)
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("your-very-secret-key-that-is-at-least-32-characters-long".getBytes());

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", Long.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // Set expiration (30 days)
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        final Long userId = extractUserId(token);
        return (username.equals(user.getUsername()) && userId.equals(user.getId()) && !isTokenExpired(token));
    }

    public Optional<User> getUserFromToken(String token) {
        if (isTokenExpired(token)) {
            return Optional.empty(); // Token is expired
        }

        try {
            Claims claims = extractAllClaims(token);
            Long userId = claims.get("id", Long.class);

            return userRepository.findById(userId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
