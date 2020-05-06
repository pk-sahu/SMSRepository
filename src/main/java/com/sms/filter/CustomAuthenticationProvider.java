package com.sms.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sms.service.UserDetailsServiceImpl;

@Component

// This dead code *************************

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String password = "";
        if(authentication.getCredentials() != null)
        	password = authentication.getCredentials().toString();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
            return auth;
        }

        return null;	
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
