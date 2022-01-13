package com.ad.adinfo.Service;

import com.ad.adinfo.Domain.Member.TokenResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10 * 10))
                .claim("emailId", tokenResponse);
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        final String jwt = builder.compact();
        log.debug("토큰 발행:{}", jwt);

        return jwt;
    }

    public boolean checkValid(String jwt) {
        System.out.println("jwt : [" + jwt + "]");
        try {
            Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            log.trace("토큰 점검 인증 실패");
        }
        return false;
    }

    public boolean readToken(final String jwt) {
        System.out.println("jwt : [" + jwt + "]");
        try {
            Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            log.trace("토큰 점검 인증 실패");
        }

        return false;
    }
}