package com.photoapp.api.users.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoapp.api.users.model.LoginRequestModel;
import com.photoapp.api.users.service.UserService;
import com.photoapp.api.users.shared.UserDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final Environment environment;
	private final UserService userService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			LoginRequestModel loginRequest = new ObjectMapper().readValue(req.getInputStream(),
					LoginRequestModel.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
					loginRequest.getPassword(), new ArrayList<>()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		User user = (User) auth.getPrincipal();
		String userName = user.getUsername();
		
		UserDTO userDTO = userService.getUserDetailsByEmail(userName);
		
		String token = createToken(userDTO.getUserId());
		
		res.addHeader("token", token);
		res.addHeader("userId", userDTO.getUserId());
	}
	
	private String createToken(String userId) {
		String token = Jwts.builder()
				.setSubject(userId)
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(getSigningKey())
				.compact();
		
		return token;
	}
	
	private Key getSigningKey() {
		String secret = environment.getProperty("token.secret");
		
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
