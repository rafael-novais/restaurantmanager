package com.novais.fiap.restaurantmanager.config.filters;

import com.novais.fiap.restaurantmanager.config.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ") ||
                request.getServletPath().startsWith("/auth") ||
                request.getServletPath().equals("/users/register") ||
                request.getRequestURI().startsWith("/swagger") ||
                request.getRequestURI().startsWith("/v3/api-docs")
        ) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String id = jwtService.extracId(token);

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String email = jwtService.extractEmail(token);
            UserDetails user = new User(
                    email,
                    "",
                    List.of(new SimpleGrantedAuthority("ROLE_" + jwtService.extractRole(token)))
            );

            if (jwtService.isValid(token)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}