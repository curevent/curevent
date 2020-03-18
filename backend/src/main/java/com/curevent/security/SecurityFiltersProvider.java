package com.curevent.security;

import com.curevent.utils.validation.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityFiltersProvider extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    private final TokenValidator tokenValidator;

    @Autowired
    public SecurityFiltersProvider(TokenProvider tokenProvider, TokenValidator tokenValidator) {
        this.tokenProvider = tokenProvider;
        this.tokenValidator = tokenValidator;
    }

    @Override
    public void configure(HttpSecurity builder) {
        builder.addFilterBefore(new AccessFilter(tokenProvider, tokenValidator), UsernamePasswordAuthenticationFilter.class);
    }
}

