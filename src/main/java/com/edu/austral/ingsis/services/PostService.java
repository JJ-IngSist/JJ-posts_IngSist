package com.edu.austral.ingsis.services;

import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.repositories.PostRepository;
import com.edu.austral.ingsis.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    post.setDate(LocalDateTime.now());
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

  public Post getFirstOfThread(Long id) {
    return getById(threadService.getByPostId(id).getFirstPostId());
  }

  public List<Post> getMostLiked(int size) {
    return postRepository.getMostLiked(size);
  }

  public List<Post> findByRegex(String value) {
    return postRepository.findByRegex(value);
  }

  public List<Post> getPostsOfFollowed(List<Long> longs) {
    List<Post> posts = new ArrayList<>();
    for (Long l : longs) {
      posts.addAll(getPostsOfUser(l));
    }
    return posts;
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
      List<Post> posts = thread.getPosts();
      thread.setPosts(new ArrayList<>());
      threadService.save(thread);
      for(Post p: posts) {
        postRepository.delete(getById(p.getId()));
      }
      threadService.delete(threadService.getById(thread.getId()));
    } else {
      threadService.deletePost(post);
      postRepository.delete(getById(post.getId()));
    }
  }
}
