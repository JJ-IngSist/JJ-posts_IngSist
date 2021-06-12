package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.dtos.thread.ThreadDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.services.ThreadService;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.edu.austral.ingsis.utils.SetUtilsToPostDTO.setDetailsToPost;
import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class ThreadController {

  private final ThreadService threadService;
  private final ObjectMapper objectMapper;
  private final PostService postService;

  public ThreadController(ThreadService threadService, PostService postService) {
    this.threadService = threadService;
    objectMapper = new ObjectMapperImpl();
    this.postService = postService;
  }

  @GetMapping("/thread/{id}")
  public ResponseEntity<ThreadDTO> getThread(@PathVariable Long id,
                                             @RequestHeader (name="Authorization") String token) {
    final Thread thread = threadService.getById(id);
    ThreadDTO threadDTO = objectMapper.map(thread, ThreadDTO.class);
    threadDTO.setPosts(objectMapper.map(thread.getPosts().stream().filter(p -> !p.getId().equals(thread.getFirstPostId())).collect(toList()), PostDTO.class));
    threadDTO.setPosts(threadDTO.getPosts().stream().map(t -> setDetailsToPost(objectMapper.map(t, Post.class), threadService, token)).collect(toList()));
    threadDTO.setFirstPost(setDetailsToPost(postService.getById(thread.getFirstPostId()), threadService, token));
    return ResponseEntity.ok(threadDTO);
  }

  @GetMapping("/post/{id}/thread")
  public ResponseEntity<ThreadDTO> getThreadOfPost(@PathVariable Long id) {
    final Thread thread = threadService.getByPostId(id);
    return ResponseEntity.ok(objectMapper.map(thread, ThreadDTO.class));
  }

  @GetMapping("/post/{id}/amount-thread")
  public int getAmountOfPostsInThread(@PathVariable String id) {
    return threadService.getByPostId(Long.parseLong(id)).getPosts().size();
  }

  @GetMapping("/thread/first/{id}")
  public ResponseEntity<PostDTO> getFirstPostOfThread(@PathVariable String id,
                                                      @RequestHeader (name="Authorization") String token) {
    Post post = postService.getById(threadService.getById(Long.parseLong(id)).getFirstPostId());
    return ResponseEntity.ok(objectMapper.map(setDetailsToPost(post, threadService, token), PostDTO.class));
  }
}
