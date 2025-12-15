package org.roka.ecomerce.auth;


import lombok.RequiredArgsConstructor;
import org.roka.ecomerce.dto.AuthRequest;
import org.roka.ecomerce.dto.AuthResponse;
import org.roka.ecomerce.dto.RegisterRequest;

import org.roka.ecomerce.entity.Role;
import org.roka.ecomerce.entity.User;
import org.roka.ecomerce.repo.UserRepo;
import org.roka.ecomerce.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // Encrypt password!
                .role(Role.USER) // Default role is USER
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // If we get here, the user is authenticated (password is correct)
        var user = repository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}
