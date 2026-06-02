package com.librework.security;

import com.librework.modules.identity.service.TokenBlacklistService;
import com.librework.modules.identity.service.TokenProviderService;
import com.librework.common.enums.ProfileType;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenService implements TokenProviderService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration:604800000}")
    private long refreshExpiration;

    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public String generateToken(String userName, UUID userId, ProfileType activeProfileType, List<String> roles) {
        return generateToken(userName, userId, jwtExpiration, "access", activeProfileType, roles);
    }

    @Override
    public String generateRefreshToken(String userName, UUID userId) {
        return generateToken(userName, userId, refreshExpiration, "refresh", null, List.of());
    }

    private String generateToken(String userName, UUID userId, long expirationMillis, String tokenType, ProfileType activeProfileType,
                                 List<String> roles) {
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                    .subject(userName)
                    .claim("userId", userId.toString())
                    .claim("userName", userName)
                    .claim("type", tokenType)
                    .claim("roles", roles)
                    .issuer("LibreWork")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + expirationMillis))
                    .jwtID(UUID.randomUUID().toString());
            if (activeProfileType != null) {
                claimsBuilder.claim("activeProfileType", activeProfileType.name());
            }
            JWTClaimsSet claimsSet = claimsBuilder.build();

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
            if (jti != null && tokenBlacklistService.isBlacklisted(jti)) {
                return false;
            }

            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean signatureValid = signedJWT.verify(new MACVerifier(secretKey.getBytes()));
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

            String type = signedJWT.getJWTClaimsSet().getStringClaim("type");

            return signatureValid && expiration != null && expiration.after(new Date())
                    && (type == null || "access".equals(type));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isRefreshTokenValid(String token) {
        try {
            String jti = getJwtId(token);
            if (jti != null && tokenBlacklistService.isBlacklisted(jti)) {
                return false;
            }

            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean signatureValid = signedJWT.verify(new MACVerifier(secretKey.getBytes()));
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            String type = signedJWT.getJWTClaimsSet().getStringClaim("type");

            return signatureValid && expiration != null && expiration.after(new Date())
                    && "refresh".equals(type);
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

    @Override
    public UUID getUserId(String token) {
        try {
            return UUID.fromString(parseClaimsSet(token).getStringClaim("userId"));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token format", e);
        }
    }

    @Override
    public String getUsername(String token) {
        try {
            return parseClaimsSet(token).getStringClaim("userName");
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token format", e);
        }
    }

    private JWTClaimsSet parseClaimsSet(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }
}
