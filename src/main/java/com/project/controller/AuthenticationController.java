package com.project.controller;

import com.project.model.request.AuthenticationRequest;
import com.project.model.response.AuthenticationResponse;
import com.project.repository.UserRepository;
import com.project.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository users;
    private final MessageSource messageSource;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository users, MessageSource messageSource) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.users = users;
        this.messageSource = messageSource;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest data) {
        try {
            String email = data.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(email, this.users.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("authController.invalidCredentials", null, null)))
                    .getRoles());
            AuthenticationResponse response = new AuthenticationResponse(email, token);
            return ok(response);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(messageSource.getMessage("authController.invalidCredentials", null, null));
        }
    }
}
