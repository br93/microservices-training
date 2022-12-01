package com.photoapp.api.gateway.filter;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CustomPreFilter implements GlobalFilter {
	
	public final Logger logger = LoggerFactory.getLogger(CustomPreFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String requestPath = exchange.getRequest().getPath().toString();
		logger.info("Request path = " + requestPath);
		
		String localAddress = exchange.getRequest().getLocalAddress().toString();
		logger.info("Local address = " + localAddress);
		
		String remoteAddress = exchange.getRequest().getRemoteAddress().toString();
		logger.info("Remote address = " + remoteAddress);
		
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		Set<String> headerNames = requestHeaders.keySet();
		
		headerNames.forEach(headerName -> {
			String headerValue = requestHeaders.getFirst(headerName);
			logger.info(headerName + " " + headerValue);
		});
		return chain.filter(exchange);
	}

}
