package io.github.oquefiz.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.oquefiz.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private static final String ISSUER = "o-que-fiz-api";

    @Value("${JWT_SECRET}")
    private String jwtSecret;

   @Value("${jwt.expiration.hours:24}")
    private int expirationHours;

    public String generateToken(User user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getUserId().toString())
                    .withClaim("name", user.getUsername())
                    .withExpiresAt(Instant.now().plus(expirationHours, ChronoUnit.HOURS))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTCreationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado.", e);
        }
    }


}
