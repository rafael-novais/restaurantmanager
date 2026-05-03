package com.novais.fiap.restaurantmanager.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novais.fiap.restaurantmanager.config.security.JwtService;
import com.novais.fiap.restaurantmanager.exceptions.InvalidCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
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

        if (request.getServletPath().startsWith("/v1/auth") ||
                request.getServletPath().equals("/v1/users/register") ||
                request.getRequestURI().startsWith("/swagger") ||
                request.getRequestURI().startsWith("/v3/api-docs") ||
                request.getServletPath().equals("/v1/auth/login")
        ) {
            chain.doFilter(request, response);
            return;
        }

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            problem.setTitle("Token invalido");
            problem.setDetail("Token ausente ou mal formatado");
            problem.setType(URI.create("/errors/invalid-token"));

            response.setStatus(401);
            response.setContentType("application/json");

            response.getWriter().write(new ObjectMapper().writeValueAsString(problem));
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