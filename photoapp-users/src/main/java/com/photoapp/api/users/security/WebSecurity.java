package com.photoapp.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.photoapp.api.users.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurity {

	private final AuthenticationManagerBuilder authManagerBuilder;
	private final Environment environment;
	private final UserService userService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/**").permitAll())
				//.hasIpAddress(environment.getProperty("gateway.ip")))
				.addFilter(getAuthenticationFilter())
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions().disable());

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	private AuthenticationFilter getAuthenticationFilter() {
		AuthenticationFilter authFilter = new AuthenticationFilter(authManagerBuilder.getOrBuild(), environment, userService);
		authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		
		return authFilter;
	}
	
	

}
