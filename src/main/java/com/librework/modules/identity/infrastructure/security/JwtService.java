package com.librework.modules.identity.infrastructure.security;

import com.librework.modules.identity.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    private RedisTokenBlacklistService blacklistService;

    public String generateToken(String userName, UUID userId) {
        try {

            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userName)
                    .claim("userId", userId.toString())
                    .claim("userName", userName)
                    .issuer("Librework")
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

    public boolean isTokenValid(String token) {
        try {
            String jti = getJwtId(token);
            if (jti != null && blacklistService.isBlacklisted(jti)) {
                return false; // Bị ném vào sổ đen -> cấm
            }

            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean signatureValid = signedJWT.verify(new MACVerifier(secretKey.getBytes()));
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            return signatureValid && expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getJwtId(String token) throws ParseException {
        return parseClaimsSet(token).getJWTID();
    }

    public Date getExpirationTime(String token) throws ParseException {
        return parseClaimsSet(token).getExpirationTime();
    }

    private JWTClaimsSet parseClaimsSet(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }
}