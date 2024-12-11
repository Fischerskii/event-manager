package ru.trofimov.eventmanager.sequrity.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenManager jwtTokenManager;

    public JwtTokenFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String tokenFromHeader = authorizationHeader.substring(7);

        String loginFromToken;
        String roleFromToken;
        try {
            loginFromToken = jwtTokenManager.getLoginFromToken(tokenFromHeader);
            roleFromToken = jwtTokenManager.getRoleFromToken(tokenFromHeader);
        } catch (Exception e) {
            log.error("Error while reading jwt token", e);
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginFromToken,
                null,
                List.of(new SimpleGrantedAuthority(roleFromToken))
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
