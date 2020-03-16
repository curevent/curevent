package com.curevent.services;

import com.curevent.exceptions.AuthenticationException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.RoleEntity;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.forms.LoginForm;
import com.curevent.models.forms.RegisterForm;
import com.curevent.models.transfers.AuthTransfer;
import com.curevent.repositories.UserRepository;
import com.curevent.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthTransfer register(RegisterForm registerForm) {
        // checking that the user exists in the database
        if (!userRepository.existsByUsername(registerForm.getUsername())) {

            // create a new refresh token
            String refreshToken = tokenProvider.createRefreshToken();

            // create instance of user model and fill it
            UserEntity userModel = UserEntity.builder()
                    .id(UUID.randomUUID())
                    .username(registerForm.getUsername())
                    .email(registerForm.getEmail())
                    .password(passwordEncoder.encode(registerForm.getPassword()))
                    .refreshToken(refreshToken)
                    .build();

            // save the user in database
            userRepository.save(userModel);

            RoleEntity role = new RoleEntity();
            role.setName("USER");

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(role);

            // create tokens transfer object and return it
            return AuthTransfer.builder()
                    .accessToken(tokenProvider.createAccessToken(userModel.getUsername(), roles))
                    .refreshToken(refreshToken)
                    .tokenType(tokenProvider.getTokenType())
                    .expiresIn(tokenProvider.getExpire())
                    .build();
        } else {
            throw new AuthenticationException("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public AuthTransfer login(LoginForm loginForm) {
        try {
            // find user from database
            UserEntity userModel = userRepository.findByUsername(loginForm.getUsername()).orElseThrow(() ->
                    new NotFoundException("The user by username [" + loginForm.getUsername() + "] not found",
                            HttpStatus.NOT_FOUND));

            // create a new refresh token
            String refreshToken = tokenProvider.createRefreshToken();

            // update refresh token
            userModel.setRefreshToken(refreshToken);

            // update user details in database
            userRepository.save(userModel);

            // authenticate this user in the authentication manager
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),
                    loginForm.getPassword()));

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(userModel.getRole());

            // create tokens transfer object and return it
            return AuthTransfer.builder()
                    .accessToken(tokenProvider.createAccessToken(userModel.getUsername(), roles))
                    .refreshToken(refreshToken)
                    .tokenType(tokenProvider.getTokenType())
                    .expiresIn(tokenProvider.getExpire())
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public UserEntity whoami(HttpServletRequest request) {
        // get username from jwt token
        String username = tokenProvider.getUsernameByToken(tokenProvider.extractToken(request));

        // find user by username from database
        UserEntity userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The user by username [" + username + "] not found",
                        HttpStatus.NOT_FOUND));

        return userModel;
    }

    public AuthTransfer refresh(String username, String refreshToken) {
        // get instance of user model by username from database
        UserEntity userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The user by username [" + username + "] not found",
                        HttpStatus.NOT_FOUND));

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userModel.getRole());

        // check for equality of refresh tokens
        if (userModel.getRefreshToken().equals(refreshToken)) {

            return AuthTransfer.builder()
                    .accessToken(tokenProvider.createAccessToken(username, roles))
                    .refreshToken(tokenProvider.createRefreshToken())
                    .tokenType(tokenProvider.getTokenType())
                    .expiresIn(tokenProvider.getExpire())
                    .build();
        } else {
            throw new AuthenticationException("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }
    }
}
