package com.photoapp.api.users.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.api.users.data.AlbumServiceClient;
import com.photoapp.api.users.data.Role;
import com.photoapp.api.users.data.UserEntity;
import com.photoapp.api.users.data.UserRepository;
import com.photoapp.api.users.model.AlbumResponseModel;
import com.photoapp.api.users.shared.UserDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	//private final RestTemplate restTemplate;
	//private final Environment env;
	private final AlbumServiceClient albumServiceClient;
		
	//Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDTO createUser(UserDTO userDetails) {

		userDetails.setUserId(UUID.randomUUID().toString());

		String encode = bCryptPasswordEncoder.encode(userDetails.getPassword());
		userDetails.setEncryptedPassword(encode);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		userEntity.setRole(Role.USER);
		
		UserEntity newUser = userRepository.save(userEntity);

		return modelMapper.map(newUser, UserDTO.class);
	}

	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		User user = new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());

		return user;
	}
	
	*/

	@Override
	public UserDetails getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return userEntity;
	}
	

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);

		//REST TEMPLATE//
		/*String albumsUrl = String.format(env.getProperty("albums.url"), userId);
		ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {});
		List<AlbumResponseModel> albumList = albumListResponse.getBody();*/
		
		List<AlbumResponseModel> albumList = albumServiceClient.getAlbums(userId);
		
		//TRY CATCH DO FEIGNEXCEPTION SEM ERRORDECODER//
		/*try {
			albumList = albumServiceClient.getAlbums(userId);
		} catch (FeignException e) {
			logger.error(e.getLocalizedMessage());
		}*/
		
				
		userDTO.setAlbums(albumList);

		return userDTO;
	}

}
