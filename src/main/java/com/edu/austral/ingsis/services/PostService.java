package com.edu.austral.ingsis.services;

import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.repositories.PostRepository;
import com.edu.austral.ingsis.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    post.setLikes(0);
    Post saved = postRepository.save(post);
    Thread thread;
    if (threadId != 0) thread = threadService.getById(threadId);
    else thread = new Thread();
    threadService.addPost(thread, saved);
    return saved;
  }

  public Post save(Post post) {
    return postRepository.save(post);
  }

  public Post getById(Long id) {
    return postRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  public List<Post> getPostsOfUser(Long id) {
    return postRepository.getPostsOfUser(id);
  }

  public List<Post> getAll() {
    return postRepository.findAll();
  }

  public Post likePost(Long id) {
    Post post = getById(id);
    post.setLikes(post.getLikes() + 1);
    return save(post);
  }

  public Post dislikePost(Long id) {
    Post post = getById(id);
    post.setLikes(post.getLikes() - 1);
    return save(post);
  }

  public List<Post> getMostLiked(int size) {
    return postRepository.getMostLiked(size);
  }

  public List<Post> findByRegex(String value) {
    return postRepository.findByRegex(value);
  }

  public Post update(Long id, Post post) {
    return postRepository
            .findById(id)
            .map(old -> {
              old.setText(post.getText());
              old.setUser(post.getUser());
              return save(old);
            })
            .orElseThrow(NotFoundException::new);
  }

  public void deletePostWithThread(Post post) {
    Thread thread = threadService.getByPostId(post.getId());
    if(post.getId().equals(thread.getFirstPostId())) {
      thread.setPosts(new ArrayList<>());
      threadService.save(thread);
      deleteAllPosts(thread);
      threadService.delete(thread);
    } else {
      threadService.deletePost(post);
      postRepository.delete(getById(post.getId()));
    }
  }

  private void deleteAllPosts(Thread thread) {
    for(Post post: thread.getPosts()) {
      postRepository.delete(getById(post.getId()));
    }
  }
}
