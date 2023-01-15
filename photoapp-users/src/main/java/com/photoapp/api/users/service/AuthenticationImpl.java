package com.photoapp.api.users.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.photoapp.api.users.config.JwtService;
import com.photoapp.api.users.data.UserEntity;
import com.photoapp.api.users.shared.AuthenticationDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {

	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	
	@Override
	public AuthenticationDTO authenticateUser(AuthenticationDTO authDetails) {
		
		var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authDetails.getEmail(), authDetails.getPassword()));
		var user = (UserEntity) auth.getPrincipal();
		
		var jwtToken = jwtService.generateToken(user);
		
		authDetails.setToken(jwtToken);
		authDetails.setUserId(user.getUserId());
		
		return authDetails;
		
	}

}
