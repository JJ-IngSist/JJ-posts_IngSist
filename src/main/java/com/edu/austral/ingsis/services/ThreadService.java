package com.edu.austral.ingsis.services;

import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.repositories.ThreadRepository;
import com.edu.austral.ingsis.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadService {

  private final ThreadRepository threadRepository;

  public ThreadService(ThreadRepository threadRepository) {
    this.threadRepository = threadRepository;
  }

  public Thread getById(Long threadId) {
    return threadRepository.findById(threadId).orElseThrow(NotFoundException::new);
  }

  public void addPost(Thread thread, Post saved) {
    thread.getPosts().add(saved);
    save(thread);
  }

  public void save(Thread thread) {
    threadRepository.save(thread);
  }

  public List<Thread> getAll() {
    return threadRepository.findAll();
  }

  public List<Thread> getAllThatContainUserPost(Long id) {
    return threadRepository.getAllThatContainUserPost(id);
  }

  public Thread getByPostId(Long id) {
    return threadRepository.getByPostId(id);
  }

  public void deletePost(Post post) {
    Thread thread = threadRepository.findThreadOfPostById(post.getId());
    thread.setPosts(thread.getPosts().stream().filter(t -> !t.getId().equals(post.getId())).collect(Collectors.toList()));
    save(thread);
  }
}
