package ar.edu.ubp.das.ristorinobackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.ttl-minutes:120}")
    private long ttlMinutes;

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generarToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();

        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
                .issuer("ristorino-backend")
                .issuedAt(now)
                .expiresAt(now.plus(ttlMinutes, ChronoUnit.MINUTES))
                .subject(subject);

        claims.forEach(builder::claim);

        JwtEncoderParameters params = JwtEncoderParameters.from(builder.build());
        return jwtEncoder.encode(params).getTokenValue();
    }

    public long getTtlMinutes() {
        return ttlMinutes;
    }
}
