package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.post.CreatePostDTO;
import com.edu.austral.ingsis.dtos.post.UpdatePostDTO;
import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.services.ThreadService;
import com.edu.austral.ingsis.utils.NotFoundException;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static com.edu.austral.ingsis.utils.ConnectMicroservices.*;
import static com.edu.austral.ingsis.utils.SetUtilsToPostDTO.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class PostController {

  private final ObjectMapper objectMapper;
  private final PostService postService;
  private final ThreadService threadService;

  public PostController(PostService postService, ThreadService threadService) {
    this.postService = postService;
    this.threadService = threadService;
    this.objectMapper = new ObjectMapperImpl();
  }

  @PostMapping("/post")
  public ResponseEntity<PostDTO> createPost(@RequestBody @Valid CreatePostDTO createPostDTO) {
    final String user = connectToUserMicroservice("/user/logged", HttpMethod.GET);
    createPostDTO.setUser(Long.valueOf(getFromJson(user, "id")));
    final Post post = postService.create(objectMapper.map(createPostDTO, Post.class), createPostDTO.getThread());
    return ResponseEntity.ok(setDetailsToPost(post, threadService));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
    final Post post = postService.getById(id);
    return ResponseEntity.ok(setDetailsToPost(post, threadService));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDTO>> getPosts() {
    final List<Post> posts = postService.getAll();
    return ResponseEntity.ok(setDetailsToPosts(posts, threadService));
  }

  @GetMapping("/search/{value}")
  public ResponseEntity<List<PostDTO>> getPostsByValue(@PathVariable String value) {
    final List<Post> posts = postService.findByRegex(value);
    return ResponseEntity.ok(setDetailsToPosts(posts, threadService));
  }

  @PostMapping("/post/like/{id}")
  public ResponseEntity<PostDTO> likePost(@PathVariable Long id) {
    final Post post = postService.likePost(id);
    return ResponseEntity.ok(setThreadId(objectMapper.map(post, PostDTO.class), threadService));
  }

  @PostMapping("/post/dislike/{id}")
  public ResponseEntity<PostDTO> dislikePost(@PathVariable Long id) {
    final Post post = postService.dislikePost(id);
    return ResponseEntity.ok(setThreadId(objectMapper.map(post, PostDTO.class), threadService));
  }

  @PutMapping("/post/{id}")
  public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,
                                            @RequestBody @Valid UpdatePostDTO updatePostDTO) {
    try {
      final Post post = postService.update(id, objectMapper.map(updatePostDTO, Post.class));
      return ResponseEntity.ok(setDetailsToPost(post, threadService));
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post doesn't exist");
    }
  }

  @DeleteMapping("/post/{id}")
  public ResponseEntity<PostDTO> deletePost(@PathVariable Long id) {
    postService.deletePostWithThread(postService.getById(id));
    return ResponseEntity.noContent().build();
  }
}
