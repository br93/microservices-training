package com.photoapp.api.users.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.photoapp.api.users.data.UserRepository;
import com.photoapp.api.users.shared.FeignErrorDecoder;

import feign.Logger;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PhotoAppUsersConfiguration {
	
	private final UserRepository userRepository;
	
	@Bean
	UserDetailsService userDetailsService() {
		return username -> userRepository.findByEmail(username)
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
		
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	 
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	Logger.Level feignLogger(){
		return Logger.Level.FULL;
	}
	
	@Bean
	FeignErrorDecoder getFeignErrorDecoder() {
		return new FeignErrorDecoder();
	}

}
