package com.devsuperior.workshopmongo.services;

import java.time.Instant;

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
	
	public Flux<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate){
		return repository.fullSearch(text, minDate, maxDate).map(x -> new PostDTO(x));
	}
	
	/*


	public List<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
		maxDate = maxDate.plusSeconds(86400); // 24 * 60 * 60
		List<PostDTO> result = repository.fullSearch(text, minDate, maxDate).stream().map(x -> new PostDTO(x)).toList();
		return result;
	}
	*/
}
