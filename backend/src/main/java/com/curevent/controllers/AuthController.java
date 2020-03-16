package com.curevent.controllers;

import com.curevent.models.forms.RegisterForm;
import com.curevent.models.transfers.AuthTransfer;
import com.curevent.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    private final Mapper mapper;
    private final AuthService authService;

    @Autowired
    public AuthController(Mapper mapper, AuthService authService) {
        this.mapper = mapper;
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthTransfer register(@RequestBody RegisterForm registerForm) {
        return authService.register(registerForm);
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public AuthTransfer login(@RequestBody LoginForm loginForm) {
        return authService.login(loginForm);
    }

    @GetMapping(value = "/whoami")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public UserTransfer whoami(HttpServletRequest request) {
        return mapper.map(authService.whoami(request), UserTransfer.class);
    }

    @GetMapping(value = "/refresh")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public AuthTransfer refresh(@RequestParam(name = "refresh_token") String refreshToken,
                                @AuthenticationPrincipal User user) {
        return authService.refresh(user.getUsername(), refreshToken);
    }
}
