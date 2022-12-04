package com.photoapp.api.albums.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.api.albums.data.AlbumEntity;
import com.photoapp.api.albums.model.AlbumResponseModel;
import com.photoapp.api.albums.service.AlbumService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/users/{id}/albums")
@AllArgsConstructor
public class AlbumController {

	private final AlbumService albumService;
	
	@GetMapping
	public List<AlbumResponseModel> userAlbums(@PathVariable String id) {

		List<AlbumResponseModel> returnValue = new ArrayList<>();
		List<AlbumEntity> albumEntities = albumService.getAlbums(id);

		if (albumEntities == null || albumEntities.isEmpty())
			return returnValue;

		Type listType = new TypeToken<List<AlbumResponseModel>>() {
		}.getType();
		
		returnValue = new ModelMapper().map(albumEntities, listType);
				
		return returnValue;
	}
}
