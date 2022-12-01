package com.photoapp.api.gateway.filter;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;
import com.photoapp.api.gateway.filter.AuthorizationHeaderFilter.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<Config>{
	
	@Autowired
	private Environment env;
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}
	
	public static class Config {
		
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			
			ServerHttpRequest request = exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
						
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer ", "");
			
			if(!isJwtValid(jwt))
				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
			
			return chain.filter(exchange);
		};
		
	}
	
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		
		return response.setComplete();
	}
	
	private Boolean isJwtValid(String jwt) {
		Boolean returnValue = true;
		
		String subject = null;
		
		try {
			subject = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody().getSubject();
		} catch (Exception ex) {
			returnValue = false;
		}
		
		if(subject == null || subject.isEmpty())
			returnValue = false;
		
		return returnValue;
	}
	
	private Key getSigningKey() {
		String secret = env.getProperty("token.secret");
		
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
