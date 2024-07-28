package com.tanthanh.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String pwd = auth.getCredentials().toString();
        UserDetails u = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(pwd, u.getPassword())){
            System.out.println("Authenticated Success");
            return new UsernamePasswordAuthenticationToken(username, pwd, Collections.emptyList());
        }else {
            System.out.println("Authentication Failed");
            throw new BadCredentialsException("User authentication failed!!!!");
        }
    }
    @Override
    public boolean supports(Class<?>auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
