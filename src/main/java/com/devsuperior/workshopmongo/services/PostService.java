package com.devsuperior.workshopmongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

	
	@Autowired
	private PostRepository repository;
	
	public Mono<PostDTO> findPostById(String id){
		return repository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("Post não encontrado!! Passe um id válido!")))
				.map(x -> new PostDTO(x));
	}
	
	public Flux<PostDTO> findPostByTitle(String text){
		return repository.findByTitleContainingIgnoreCase(text).map(x -> new PostDTO(x));
	}
	
	/*

	@Transactional(readOnly = true)
	public PostDTO findById(String id) {
		Post post = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new PostDTO(post);
	}
	
	public List<PostDTO> findByTitle(String text) {
		List<PostDTO> result = repository.searchTitle(text).stream().map(x -> new PostDTO(x)).toList();
		return result;
	}
	
	public List<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
		maxDate = maxDate.plusSeconds(86400); // 24 * 60 * 60
		List<PostDTO> result = repository.fullSearch(text, minDate, maxDate).stream().map(x -> new PostDTO(x)).toList();
		return result;
	}
	*/
}
