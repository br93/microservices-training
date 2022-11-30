package com.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.api.users.data.UserEntity;
import com.photoapp.api.users.data.UserRepository;
import com.photoapp.api.users.shared.UserDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		
		String encode = bCryptPasswordEncoder.encode(userDetails.getPassword());
		userDetails.setEncryptedPassword(encode);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		UserEntity newUser = userRepository.save(userEntity);
		
		System.out.println(newUser.getEncryptedPassword());
		
		return modelMapper.map(newUser, UserDTO.class);
	}

}
