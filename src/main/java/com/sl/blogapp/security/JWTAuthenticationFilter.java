package com.sl.blogapp.security;

import com.sl.blogapp.users.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class JWTAuthenticationFilter extends AuthenticationFilter {

    private JWTAuthenticationManager jwtAuthenticationManager;
    public JWTAuthenticationFilter(JWTAuthenticationManager jwtAuthenticationManager) {
        super(jwtAuthenticationManager, new JWTAuthenticationConverter());

        this.setSuccessHandler((request, response, authentication) ->
                SecurityContextHolder.getContext().setAuthentication(authentication)
        );
    }
}
