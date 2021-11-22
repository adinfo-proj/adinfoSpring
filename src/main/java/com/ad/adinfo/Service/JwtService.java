package com.ad.adinfo.Service;

import com.ad.adinfo.Domain.TokenResponse;
import io.jsonwebtoken.*;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtService {

    private String expireMin    = "5";
    private String salt         = "adinfo.co.kr";

    public String create(final TokenResponse tokenResponse) {
        log.trace("time : {}", expireMin);

        final JwtBuilder builder = Jwts.builder();

        builder.setHeaderParam("typ", "JWT");

        builder.setSubject("access_token")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .claim("emailId", tokenResponse);
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        final String jwt = builder.compact();
        log.debug("토큰 발행:{}", jwt);

        return jwt;
    }

    public void checkValid(final String jwt) {
        log.trace("토큰 점검 : {}", jwt);
        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
    }

    public Map<String, Object> readToken(final String jwt) {
        Jws<Claims> claims = null;

        System.out.println("jwt : [" + jwt + "]");
        try {
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        log.trace("claims: {}", claims);

        return claims.getBody();
    }


}
