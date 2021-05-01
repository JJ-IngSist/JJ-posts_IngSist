package com.edu.austral.ingsis.repositories;

import com.edu.austral.ingsis.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

  Optional<Post> findByUserId(Long user_id);
}
