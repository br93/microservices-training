package com.photoapp.api.albums.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity implements Serializable {
	
	private static final long serialVersionUID = -1813195709018144207L;
	
	private Long id;
	private String albumId;
	
	private String userId;
	private String name;
	private String description;

}
