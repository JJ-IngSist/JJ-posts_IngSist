package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.thread.ThreadDTO;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.services.ThreadService;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class ThreadController {

  private final ThreadService threadService;
  private final ObjectMapper objectMapper;

  public ThreadController(ThreadService threadService) {
    this.threadService = threadService;
    this.objectMapper = new ObjectMapperImpl();
  }

  @GetMapping("/post/{id}/thread")
  public ResponseEntity<ThreadDTO> getThreadOfPost(@PathVariable Long id) {
    final Thread thread = threadService.getByPostId(id);
    return ResponseEntity.ok(objectMapper.map(thread, ThreadDTO.class));
  }
}
