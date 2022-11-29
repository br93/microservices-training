package com.photoapp.api.users.service;

import java.util.UUID;

import com.photoapp.api.users.shared.UserDTO;

public class UserServiceImpl implements UserService{

	@Override
	public UserDTO createUser(UserDTO userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		return null;
	}

}
