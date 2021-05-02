package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class UserPostController {

  private final ObjectMapper objectMapper;
  private final PostService postService;

  public UserPostController(PostService postService) {
    this.postService = postService;
    this.objectMapper = new ObjectMapperImpl();
  }

  @GetMapping("/user/{id}/posts")
  public ResponseEntity<List<PostDTO>> getPostsOfUser(@PathVariable Long id) {
    final List<Post> posts = postService.getPostsOfUser(id);
    return ResponseEntity.ok(objectMapper.map(posts, PostDTO.class));
  }
}
