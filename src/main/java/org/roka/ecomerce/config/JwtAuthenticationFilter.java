package org.roka.ecomerce.config;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.roka.ecomerce.security.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, java.io.IOException {

        // 1. Check if the request has a JWT token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // If no header or doesn't start with "Bearer ", just continue (User is anonymous)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract the token
        jwt = authHeader.substring(7); // Remove "Bearer " prefix
        userEmail = jwtService.extractUsername(jwt); // Extract Email from token

        // 3. If User is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Get User details from Database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 4. Validate Token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Create an Authentication Token (Standard Spring Security Object)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 5. Update the Security Context (Log the user in for this request)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Pass the request to the next filter
        filterChain.doFilter(request, response);
    }
}