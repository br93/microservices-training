package com.photoapp.api.users.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.api.users.model.AuthenticationResponseModel;
import com.photoapp.api.users.model.LoginRequestModel;
import com.photoapp.api.users.service.AuthenticationService;
import com.photoapp.api.users.shared.AuthenticationDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authService;
	
	@PostMapping
	public ResponseEntity<AuthenticationResponseModel> authenticate(@RequestBody LoginRequestModel request){
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		AuthenticationDTO authDTO = modelMapper.map(request, AuthenticationDTO.class);
		AuthenticationDTO auth = authService.authenticateUser(authDTO);
		
		AuthenticationResponseModel authResponse = modelMapper.map(auth, AuthenticationResponseModel.class);
		
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
}
