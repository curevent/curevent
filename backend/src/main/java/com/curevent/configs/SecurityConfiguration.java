package com.curevent.configs;

import com.curevent.security.SecurityFiltersProvider;
import com.curevent.security.TokenProvider;
import com.curevent.utils.validation.TokenValidator;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final TokenValidator tokenValidator;

    @Autowired
    public SecurityConfiguration(TokenProvider tokenProvider, TokenValidator tokenValidator) {
        this.tokenProvider = tokenProvider;
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // Entry points
        http.authorizeRequests()//
                .antMatchers("/login").permitAll()//
                .antMatchers("/register").permitAll()//
                .antMatchers("/users/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/demo").permitAll()
                .antMatchers("/h2-console/**/**").permitAll()

                // Disallow everything else..
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/error");

        // Apply tokenProvider
        http.apply(new SecurityFiltersProvider(tokenProvider, tokenValidator));

        // Optional, if you want to test the API from a browser
        // http.httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
