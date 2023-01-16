package com.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.photoapp.api.users.shared.UserDTO;

public interface UserService {
	
	UserDTO createUser (UserDTO userDetails);
	UserDetails getUserDetailsByEmail (String email);
	UserDTO getUserByUserId (String userId);

}
