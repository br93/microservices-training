package com.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photoapp.api.users.shared.UserDTO;

public interface UserService extends UserDetailsService {
	
	UserDTO createUser (UserDTO userDetails);

}
