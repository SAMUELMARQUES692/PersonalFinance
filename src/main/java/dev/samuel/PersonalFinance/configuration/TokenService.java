package dev.samuel.PersonalFinance.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.samuel.PersonalFinance.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenService {

    private final Algorithm algorithm;
    private final long expirationSeconds;

    public TokenService(
            @Value("${personalfinance.security.secret}") String secret,
            @Value("${personalfinance.security.expiration:86400}") long expirationSeconds
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(UserModel user) {
        Instant now = Instant.now();
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirationSeconds))
                .withIssuer("API Personal Finance")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("API Personal Finance")
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .id(jwt.getClaim("userId").asLong())
                    .name(jwt.getClaim("name").asString())
                    .email(jwt.getSubject())
                    .build());

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}