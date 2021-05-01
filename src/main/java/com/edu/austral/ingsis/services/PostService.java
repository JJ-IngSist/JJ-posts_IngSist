package com.edu.austral.ingsis.services;

import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.repositories.PostRepository;
import com.edu.austral.ingsis.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final ThreadService threadService;

  public PostService(PostRepository postRepository, ThreadService threadService) {
    this.postRepository = postRepository;
    this.threadService = threadService;
  }

  public Post create(Post post, Long threadId) {
    post.setDate(LocalDate.now());
    Post saved = postRepository.save(post);
    if (threadId != 0) {
      Thread thread = threadService.getById(threadId);
      threadService.addPost(thread, saved);
    }
    return saved;
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

  public void delete(Post post) {
    threadService.deletePost(post);
    postRepository.delete(getById(post.getId()));
  }

  public List<Post> getAll() {
    return postRepository.findAll();
  }
}
