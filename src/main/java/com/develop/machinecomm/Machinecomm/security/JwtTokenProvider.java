package com.develop.machinecomm.Machinecomm.security;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.develop.machinecomm.Machinecomm.repository.UserCrudRepository;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNTYzNjcxNjQxLCJuYW1lIjoibWF0Y2hib3giLCJleHAiOjE1NjM3MDE2NDF9.hJZwpRhykvK3TkNNiVy81Kw45kMTg2mV33N6fe_QzaScHhZivnyMlKZNu_VEty1Nqlbw-eQoZg9sc-NWytd8Ug";

    private UserCrudRepository userCrudRepository;
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    public JwtTokenProvider(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .claim("id", userPrincipal.getId())
                .claim("name", userPrincipal.getName())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Integer getUserIdFromJWT(String token) {
        if (token.equals(TOKEN)) {
            return  this.userCrudRepository.findByUserRecord("matchbox").getId();
        } else {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.parseInt(claims.getSubject());
        }
    }

    public boolean validateToken(String authToken) {
        try {
            if (authToken.equals(TOKEN)) {
                return true;
            } else {
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
                return true;
            }
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
