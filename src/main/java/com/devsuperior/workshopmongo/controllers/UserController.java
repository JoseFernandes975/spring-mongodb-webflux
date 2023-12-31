package com.devsuperior.workshopmongo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	
	@GetMapping
	public Flux<UserDTO> findAll(){
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Mono<ResponseEntity<UserDTO>> findById(@PathVariable String id){
		return service.findById(id).map(x -> ResponseEntity.ok(x));
	}
	
	@PostMapping
	public Mono<ResponseEntity<UserDTO>> insertUser(@RequestBody UserDTO dto, UriComponentsBuilder uri){
		return service.insertUser(dto)
				.map(x -> ResponseEntity.created(uri.path("/{id").buildAndExpand(x.getId()).toUri()).body(x));
	}
	
	@PutMapping(value = "/{id}")
	public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable String id, @RequestBody UserDTO dto){
		return service.updateUser(id, dto).map(x -> ResponseEntity.ok().body(x));
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id){
		return service.deleteUser(id).then(Mono.just(ResponseEntity.noContent().<Void>build()));
	}
	
	/*
	

	
	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<PostDTO>> findPosts(@PathVariable String id) {
		List<PostDTO> list = service.findPosts(id);
		return ResponseEntity.ok().body(list);
	}

*/
}
