package com.edu.austral.ingsis.services;

import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.repositories.PostRepository;
import com.edu.austral.ingsis.utils.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Post create(Post post) {
    return postRepository.save(post);
  }

  public Post save(Post post) {
    return postRepository.save(post);
  }

  public Post getById(Long id) {
    return postRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  public Post update(Long id, Post post) {
    return postRepository
            .findById(id)
            .map(old -> {
              old.setText(post.getText());
              old.setUserId(post.getUserId());
              return save(old);
            })
            .orElseThrow(NotFoundException::new);
  }

  public void delete(Long id) {
    postRepository.delete(getById(id));
  }
}
