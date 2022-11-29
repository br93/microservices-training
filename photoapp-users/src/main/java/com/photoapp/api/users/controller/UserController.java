package com.photoapp.api.users.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class UserController {

	private final Environment env;

	@GetMapping("status/check")
	public String status() {
		return "Ok, on port: " + env.getProperty("local.server.port");
	}

}
