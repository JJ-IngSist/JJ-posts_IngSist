package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.post.CreatePostDTO;
import com.edu.austral.ingsis.dtos.post.UpdatePostDTO;
import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.utils.NotFoundException;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static com.edu.austral.ingsis.utils.ConnectMicroservices.getFromJson;
import static com.edu.austral.ingsis.utils.ConnectMicroservices.getRequestEntity;

@RestController
public class PostController {

  private final com.edu.austral.ingsis.utils.ObjectMapper objectMapper;
  private final PostService postService;
  private final RestTemplate restTemplate;

  public PostController(PostService postService) {
    this.postService = postService;
    this.objectMapper = new ObjectMapperImpl();
    restTemplate = new RestTemplate();
  }

  @PostMapping("/create")
  public ResponseEntity<PostDTO> createPost(@RequestBody @Valid CreatePostDTO createPostDTO) {
    final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/logged",
            HttpMethod.GET,
            getRequestEntity(),
            String.class);
    final String user = responseEntity.getBody();
    createPostDTO.setUserId(Long.valueOf(getFromJson(user, "id")));
    final Post post = postService.create(objectMapper.map(createPostDTO, Post.class), createPostDTO.getThreadId());
    return ResponseEntity.ok(objectMapper.map(post, PostDTO.class));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
    final Post post = postService.getById(id);
    return ResponseEntity.ok(objectMapper.map(post, PostDTO.class));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDTO>> getPosts() {
    final List<Post> posts = postService.getAll();
    return ResponseEntity.ok(objectMapper.map(posts, PostDTO.class));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,
                                            @RequestBody @Valid UpdatePostDTO updatePostDTO) {
    try {
      final Post post = postService.update(id, objectMapper.map(updatePostDTO, Post.class));
      return ResponseEntity.ok(objectMapper.map(post, PostDTO.class));
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post doesn't exist");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<PostDTO> updatePost(@PathVariable Long id) {
    postService.delete(postService.getById(id));
    return ResponseEntity.noContent().build();
  }
}
