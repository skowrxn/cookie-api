package pl.skowrxn.cookie.admin.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import pl.skowrxn.cookie.admin.auth.service.UserDetailsImpl;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Setter
@ConfigurationProperties(prefix = "app.jwt")
public class JWTUtils {

    private String secret;
    private long expirationMs;
    private String cookieName;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JWTUtils.class);

    public String getJWTFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie == null) {
            logger.debug("Cookie not found: {}", cookieName);
            return null;
        } else {
            return cookie.getValue();
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromEmail(userPrincipal.getEmail());
        return ResponseCookie.from(cookieName, jwt)
            .path("/")
            .maxAge(24 * 60 * 60)
            .httpOnly(true)
            .build();
    }

    public String generateTokenFromEmail(String email) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret));
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + expirationMs))
            .signWith(key)
            .compact();
    }

    public ResponseCookie generateCleanJwtCookie() {
        return ResponseCookie.from(cookieName, null)
            .path("/")
            .maxAge(0)
            .httpOnly(true)
            .build();
    }


    public String generateJWTToken(String email) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret));
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + expirationMs))
            .signWith(key)
            .compact();
    }

    public String getEmailFromJWT(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload().getSubject();
    }

    public boolean validateJwtToken(String token) {
        if (token == null || token.isEmpty()) {
            logger.error("Tried to validate an empty JWT");
            return false;
        }

        if (this.secret == null || this.secret.isEmpty()) {
            logger.error("Secret key is empty or undefined");
            return false;
        }

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            return false;
        }

        return false;
    }

}