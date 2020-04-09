package com.project.service;

import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository users;
    private final MessageSource messageSource;

    public CustomUserDetailsService(UserRepository users, MessageSource messageSource) {
        this.users = users;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // email is used as username
        return this.users.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(messageSource.getMessage("authController.invalidCredentials", null, null))
        );
    }
}
