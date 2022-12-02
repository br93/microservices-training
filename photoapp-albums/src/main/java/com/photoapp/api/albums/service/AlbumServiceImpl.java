package com.photoapp.api.albums.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.photoapp.api.albums.data.AlbumEntity;

@Service
public class AlbumServiceImpl implements AlbumService{

	@Override
	public List<AlbumEntity> getAlbums(String userId) {
		AlbumEntity albumEntity = new AlbumEntity(1L, "album1", userId, "Album 1", "Album 1");
		AlbumEntity albumEntity2 = new AlbumEntity(2L, "album2", userId, "Album 2", "Album 2");
		
		List<AlbumEntity> returnValue = Arrays.asList(albumEntity, albumEntity2);
		
		return returnValue;
	}

	
}
