package com.curevent.security;

import com.curevent.exceptions.AuthenticationException;
import com.curevent.models.transfers.ErrorTransfer;
import com.curevent.utils.validation.TokenValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AccessFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final TokenValidator tokenValidator;

    @Autowired
    public AccessFilter(TokenProvider tokenProvider, TokenValidator tokenValidator) {
        this.tokenProvider = tokenProvider;
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.extractToken(httpServletRequest);
        String secretKey = tokenProvider.getSecretKey();
        try {
            if (token != null && tokenValidator.validateToken(token, secretKey)) {
                Authentication auth = tokenProvider.authenticateToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            ErrorTransfer responseInfo = new ErrorTransfer(e.getMessage(), e.getHttpStatus().value());
            httpServletResponse.resetBuffer();
            httpServletResponse.setStatus(e.getHttpStatus().value());
            httpServletResponse.setHeader("Content-Type", "application/json");
            httpServletResponse.getOutputStream().print(new ObjectMapper().writeValueAsString(responseInfo));
            httpServletResponse.flushBuffer();
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
