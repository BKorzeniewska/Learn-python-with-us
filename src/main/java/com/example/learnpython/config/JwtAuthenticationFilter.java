package com.example.learnpython.config;


import com.example.learnpython.token.ExpiredTokenException;
import com.example.learnpython.token.TokenRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final static Gson GSON = new Gson();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        response.setContentType("application/json");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException ex) {
            final ExpiredTokenException exception = new ExpiredTokenException("Token expired", "TOKEN_EXPIRED", HttpStatus.BAD_REQUEST);
            response.getWriter().write(GSON.toJson(exception));
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        } catch (ClaimJwtException ex) {
            final ExpiredTokenException exception = new ExpiredTokenException("Claim exception", "CLAIM_EXCEPTION", HttpStatus.BAD_REQUEST);
            response.getWriter().write(GSON.toJson(exception));
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        } catch (Exception ex) {
            final ExpiredTokenException exception = new ExpiredTokenException("Unknown error", "ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
            response.getWriter().write(GSON.toJson(exception));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}