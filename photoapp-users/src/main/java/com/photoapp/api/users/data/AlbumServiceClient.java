package com.photoapp.api.users.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.photoapp.api.users.model.AlbumResponseModel;

@FeignClient(name="albums-ms")
public interface AlbumServiceClient {

	@GetMapping("/v1/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
