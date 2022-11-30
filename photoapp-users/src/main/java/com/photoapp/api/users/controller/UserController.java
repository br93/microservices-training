package com.photoapp.api.users.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.api.users.model.CreateUserRequestModel;
import com.photoapp.api.users.model.CreateUserResponseModel;
import com.photoapp.api.users.service.UserService;
import com.photoapp.api.users.shared.UserDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class UserController {

	private final Environment env;
	private final UserService userService;

	@GetMapping("status/check")
	public String status() {
		return "Ok, on port: " + env.getProperty("local.server.port");
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);
		UserDTO newUser = userService.createUser(userDTO);
		
		CreateUserResponseModel userResponse = modelMapper.map(newUser, CreateUserResponseModel.class);
		
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}

}
