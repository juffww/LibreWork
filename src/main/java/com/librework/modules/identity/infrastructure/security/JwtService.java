package com.librework.modules.identity.infrastructure.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(String username) {
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("librework")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + jwtExpiration))
                    .build();

            JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(claimsSet.toJSONObject()));
            jwsObject.sign(new MACSigner(secretKey.getBytes()));

            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Cannot create JWT token", e);
            throw new RuntimeException("Cannot create JWT token", e);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean signatureValid = signedJWT.verify(new MACVerifier(secretKey.getBytes()));
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            return signatureValid && expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private JWTClaimsSet parseClaimsSet(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }
}