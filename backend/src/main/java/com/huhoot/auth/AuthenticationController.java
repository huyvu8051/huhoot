package com.huhoot.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;

    private final JwtUtil jwtUtil;

    @PostMapping("/authentication")
    public AuthenticationResponse createAuthenticationToken(@RequestBody @Valid AuthenticationRequest request) {

        String formattedUsername = request.getUsername().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(formattedUsername, request.getPassword()));

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(formattedUsername);

        final String jwt = jwtUtil.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .jwt(jwt)
                .username(userDetails.getUsername())
                .authorities(userDetails.getAuthorities())
                .build();
    }
}
