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
import java.util.ArrayList;
import java.util.List;

import static com.edu.austral.ingsis.utils.ConnectMicroservices.*;

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
    return ResponseEntity.ok(setThreadId(setUserDetails(user, objectMapper.map(post, PostDTO.class))));
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
    final Post post = postService.getById(id);
    final String user = connectToUserMicroservice("/user/" + post.getUser(), HttpMethod.GET);
    PostDTO postDTO = setUserDetails(user, objectMapper.map(post, PostDTO.class));
    return ResponseEntity.ok(setThreadId(postDTO));
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDTO>> getPosts() {
    final List<Post> posts = postService.getAll();
    List<PostDTO> updatedPostDTOS = new ArrayList<>();
    for(PostDTO dto : objectMapper.map(posts, PostDTO.class)) {
      updatedPostDTOS.add(setThreadId(setUserDetails(connectToUserMicroservice("/user/" + dto.getUser(), HttpMethod.GET), dto)));
    }
    return ResponseEntity.ok(updatedPostDTOS);
  }

  @GetMapping("/search/{value}")
  public ResponseEntity<List<PostDTO>> getPostsByValue(@PathVariable String value) {
    final List<Post> posts = postService.findByRegex(value);
    List<PostDTO> updatedPostDTOS = new ArrayList<>();
    for(PostDTO dto : objectMapper.map(posts, PostDTO.class)) {
      updatedPostDTOS.add(setThreadId(setUserDetails(connectToUserMicroservice("/user/" + dto.getUser(), HttpMethod.GET), dto)));
    }
    return ResponseEntity.ok(updatedPostDTOS);
  }

  @PostMapping("/post/like/{id}")
  public ResponseEntity<PostDTO> likePost(@PathVariable Long id) {
    final Post post = postService.likePost(id);
    return ResponseEntity.ok(setThreadId(objectMapper.map(post, PostDTO.class)));
  }

  @PostMapping("/post/dislike/{id}")
  public ResponseEntity<PostDTO> dislikePost(@PathVariable Long id) {
    final Post post = postService.dislikePost(id);
    return ResponseEntity.ok(setThreadId(objectMapper.map(post, PostDTO.class)));
  }

  @PutMapping("/post/{id}")
  public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,
                                            @RequestBody @Valid UpdatePostDTO updatePostDTO) {
    try {
      final Post post = postService.update(id, objectMapper.map(updatePostDTO, Post.class));
      return ResponseEntity.ok(setThreadId(objectMapper.map(post, PostDTO.class)));
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The post doesn't exist");
    }
  }

  @DeleteMapping("/post/{id}")
  public ResponseEntity<PostDTO> deletePost(@PathVariable Long id) {
    postService.deletePostWithThread(postService.getById(id));
    return ResponseEntity.noContent().build();
  }

  private PostDTO setThreadId(PostDTO postDTO) {
    postDTO.setThreadId(threadService.getByPostId(postDTO.getId()).getId());
    return postDTO;
  }
}
