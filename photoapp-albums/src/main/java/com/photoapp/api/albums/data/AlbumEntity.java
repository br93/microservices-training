package com.photoapp.api.albums.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity {
	
	private Long id;
	private String albumId;
	private String userId;
	
	private String name;
	private String description;

}
