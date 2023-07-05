package com.example.cheongchun28.global.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // get JWT at Header
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        //Token 유효성 검사
        if (token != null && jwtUtil.validateToken(token)) {
            // get info at token
            Authentication authentication = jwtUtil.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
