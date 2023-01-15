package com.photoapp.api.users.shared;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO implements Serializable{

	private static final long serialVersionUID = 156800928500353903L;

	private String userId;
	private String email;
	private String password;
	private String token;
}
