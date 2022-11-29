package com.photoapp.api.users.shared;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = -3756968831006535620L;

	private String userId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	private String encryptedPassword;
}
