package io.wisoft.capstonedesign.global.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Component
public class JwtTokenProvider {

    @Value("${jwt.access_token.expire_length}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh_token.expire_length}")
    private long refreshTokenValidityInMilliseconds;

    @Value("${jwt.token.secret_key}")
    private String secretKey;

    public String createAccessToken(final String payload) {

        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken() {

        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        return createToken(generatedString, refreshTokenValidityInMilliseconds);
    }

    public String createToken(final String payload, final long expireLength) {

        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPayload(final String token) {

        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean validateToken(final String token) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
