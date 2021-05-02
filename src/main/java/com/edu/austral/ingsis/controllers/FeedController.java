package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedController {

  private final ObjectMapper objectMapper;
  private final PostService postService;

  public FeedController(PostService postService) {
    this.postService = postService;
    this.objectMapper = new ObjectMapperImpl();
  }

  @GetMapping("/posts/most-liked")
  public ResponseEntity<List<PostDTO>> getMostLikedPosts(@RequestParam(name = "size", defaultValue = "10") int size) {
    final List<Post> posts = postService.getMostLiked(size);
    return ResponseEntity.ok(objectMapper.map(posts, PostDTO.class));
  }
}
