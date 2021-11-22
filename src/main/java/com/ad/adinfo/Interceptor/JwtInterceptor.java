package com.ad.adinfo.Interceptor;

import com.ad.adinfo.Service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        System.out.println(req.getMethod() + " :" + req.getServletPath());

        if(req.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            String token = req.getHeader("jwt-auth-token");
            if(token != null || token.length() > 0) {
                jwtService.checkValid(token);
                log.trace("토큰 사용 가능: {}", token);
                return true;
            } else {
                throw new RuntimeException("인증 토큰이 없습니다.");
            }
        }
    }
}
