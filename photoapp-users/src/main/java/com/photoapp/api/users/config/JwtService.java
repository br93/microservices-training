package com.photoapp.api.users.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	
	private final Environment environment;
	
	public String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}
	
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsername(token);
		Boolean isTokenValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		
		return isTokenValid;
	}
	
	private Boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}
	
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}
	
	private Claims getAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSigningKey() {
		String secret = environment.getProperty("token.secret");
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
