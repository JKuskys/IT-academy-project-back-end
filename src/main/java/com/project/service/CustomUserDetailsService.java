package com.project.service;

import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {              // email is used as username
            return this.users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Neteisingas vartotojo paštas arba slaptažodis"));
    }
}
