package com.swerve.backend.auth.security;

import com.swerve.backend.shared.security.AuthenticationTokenFilter;
import com.swerve.backend.shared.security.TokenUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.swerve.backend.shared.client.AuthFeignClient;

@Component
public class AuthTokenFilter extends AuthenticationTokenFilter {
    
    private final UserDetailsService userDetailsService;

    public AuthTokenFilter( TokenUtils tokenUtils, AuthFeignClient authFeignClient, UserDetailsService userDetailsService) {
        // AuthFeignClient isn't need anymore because getUserDetails is overriden
        super( authFeignClient, tokenUtils );
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected UserDetails getUserDetails(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}
