package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.thread.ThreadDTO;
import com.edu.austral.ingsis.entities.Thread;
import com.edu.austral.ingsis.services.ThreadService;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

  private final ObjectMapper objectMapper;
  private final ThreadService threadService;

  public HomeController(ThreadService threadService) {
    this.threadService = threadService;
    this.objectMapper = new ObjectMapperImpl();
  }

  @GetMapping("/threads/{id}")
  public ResponseEntity<List<ThreadDTO>> getThread(@PathVariable Long id) {
    final List<Thread> threads = threadService.getAllThatContainUserPost(id);
    return ResponseEntity.ok(objectMapper.map(threads, ThreadDTO.class));
  }
}
