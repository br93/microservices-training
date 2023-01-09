package com.photoapp.api.users.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.photoapp.api.users.model.AlbumResponseModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name="albums-ms")
public interface AlbumServiceClient {

	@GetMapping("/v1/users/{id}/albums")
	@CircuitBreaker(name = "albums-ms", fallbackMethod = "getAlbumsFallback")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
	
	default List<AlbumResponseModel> getAlbumsFallback(String id, Throwable exception){
		return new ArrayList<>();
	}
}
