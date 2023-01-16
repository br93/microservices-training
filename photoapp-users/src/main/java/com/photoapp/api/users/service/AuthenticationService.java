package com.photoapp.api.users.service;

import com.photoapp.api.users.shared.AuthenticationDTO;

public interface AuthenticationService {

	AuthenticationDTO authenticateUser(AuthenticationDTO authDetails);
}
