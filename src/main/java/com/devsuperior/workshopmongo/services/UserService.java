package com.devsuperior.workshopmongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.entities.User;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	
	@Autowired
	private UserRepository repository;

	public Flux<UserDTO> findAll(){
		return repository.findAll().map(x -> new UserDTO(x));
	}
	
	public Mono<UserDTO> findById(String id){
		return repository.findById(id).map(x -> new UserDTO(x))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuário não encontrado!")));
	}
	
	public Mono<UserDTO> insertUser(UserDTO dto){
		User entity = new User();
		copyDtoToEntity(dto, entity);
		Mono<UserDTO> result = repository.save(entity).map(x -> new UserDTO(entity));
		return result;
	}
	
	private static void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
	}
	
}
