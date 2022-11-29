package com.photoapp.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {

	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	                .authorizeHttpRequests((authorize) -> {
						try {
							authorize
							        .mvcMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
							        .anyRequest().permitAll().and().csrf().disable();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}); 

	        return http.build();
	    }

}
