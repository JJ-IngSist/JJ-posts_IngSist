package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.thread.ThreadDTO;
import com.edu.austral.ingsis.entities.Thread;
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

  public ThreadController(ThreadService threadService) {
    this.threadService = threadService;
    this.objectMapper = new ObjectMapperImpl();
  }

  @GetMapping("/thread/{id}")
  public ResponseEntity<ThreadDTO> getThread(@PathVariable Long id,
                                             @RequestHeader (name="Authorization") String token) {
    final Thread thread = threadService.getById(id);
    ThreadDTO threadDTO = objectMapper.map(thread, ThreadDTO.class);
    threadDTO.setPosts(thread.getPosts().stream().map(t -> setDetailsToPost(t, threadService, token)).collect(toList()));
    return ResponseEntity.ok(threadDTO);
  }

  @GetMapping("/post/{id}/thread")
  public ResponseEntity<ThreadDTO> getThreadOfPost(@PathVariable Long id) {
    final Thread thread = threadService.getByPostId(id);
    return ResponseEntity.ok(objectMapper.map(thread, ThreadDTO.class));
  }
}
