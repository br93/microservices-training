package com.photoapp.api.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {
	
	public final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

	@Bean
	public GlobalFilter secondFilter() {
		return(exchange, chain) -> {
			logger.info("Second Pre-Filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				logger.info("Second Post-Filter");
			}));
		};
	}
}
