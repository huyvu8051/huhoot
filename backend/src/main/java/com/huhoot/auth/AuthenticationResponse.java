package com.huhoot.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
@Getter
public class AuthenticationResponse {
	private final String jwt;
	private final String username;
	private final Collection<? extends GrantedAuthority> authorities;
}
