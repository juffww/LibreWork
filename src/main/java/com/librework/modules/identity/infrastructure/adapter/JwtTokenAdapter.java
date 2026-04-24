package com.librework.modules.identity.infrastructure.adapter;

import com.librework.modules.identity.application.port.out.TokenBlacklistPort;
import com.librework.modules.identity.application.port.out.TokenProviderPort;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenProviderPort {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // Inject Port thay vì Implement cụ thể của Redis
    private final TokenBlacklistPort blacklistPort;

    @Override
    public String generateToken(String userName, UUID userId) {
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userName)
                    .claim("userId", userId.toString())
                    .claim("userName", userName)
                    .issuer("LibreWork")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + jwtExpiration))
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(claimsSet.toJSONObject()));
            jwsObject.sign(new MACSigner(secretKey.getBytes()));

            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Cannot create JWT token", e);
            throw new RuntimeException("Cannot create JWT token", e);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            String jti = getJwtId(token);
            // Gọi qua Port thay vì class cụ thể
            if (jti != null && blacklistPort.isBlacklisted(jti)) {
                return false;
            }

            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean signatureValid = signedJWT.verify(new MACVerifier(secretKey.getBytes()));
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

            return signatureValid && expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getJwtId(String token) {
        try {
            return parseClaimsSet(token).getJWTID();
        } catch (ParseException e) {
            // Che giấu ngoại lệ của Nimbus, chỉ ném ra ngoại lệ chung
            throw new RuntimeException("Invalid token format", e);
        }
    }

    @Override
    public Date getExpirationTime(String token) {
        try {
            return parseClaimsSet(token).getExpirationTime();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token format", e);
        }
    }

    private JWTClaimsSet parseClaimsSet(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }
}