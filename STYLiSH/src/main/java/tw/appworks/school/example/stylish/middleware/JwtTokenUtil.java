package tw.appworks.school.example.stylish.middleware;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private final String CLAIMS_KEY_USER_ROLES = "userRoles";

    private @Value("${jwt.signKey}") String jwtSignKey;

    private @Value("${jwt.expireTimeAsSec}") long jwtExpireTimeAsSec; // 30 day in sec

    public Boolean validate(String token) {
        final String username = getUsernameFromToken(token);
        return (username != null && !isTokenExpired(token));
    }

    public String generateToken(String subject, List<String> userRoles) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(Map.of(CLAIMS_KEY_USER_ROLES, userRoles))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpireTimeAsSec))) // JWT
                // 過期時間
                .signWith(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<SimpleGrantedAuthority> getUserAuthoritiesFromToken(String token) {
        List<String> userRoles = parseToken(token).get(CLAIMS_KEY_USER_ROLES, List.class);
        return userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Date getExpirationDateFromToken(String token) {
        return parseToken(token).getExpiration();
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSignKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
