package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.home.HomeDTO;
import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.services.ThreadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.edu.austral.ingsis.utils.SetUtilsToPostDTO.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class FeedController {

  private final PostService postService;
  private final ThreadService threadService;

  public FeedController(PostService postService, ThreadService threadService) {
    this.postService = postService;
    this.threadService = threadService;
  }

  @GetMapping("/posts/most-liked")
  public ResponseEntity<List<HomeDTO>> getMostLikedPosts(@RequestParam(name = "size", defaultValue = "10") int size,
                                                         @RequestHeader (name="Authorization") String token) {
    final List<Post> posts = postService.getMostLiked(size);
    final List<HomeDTO> homes = new ArrayList<>();
    for (Post post : sortByDate(posts)) {
      final Post first = postService.getFirstOfThread(post.getId());
      final PostDTO firstDTO = setDetailsToPost(first, threadService, token);
      final PostDTO postDTO = setLiked(setDetailsToPost(post, threadService, token), token);
      homes.add(new HomeDTO(firstDTO, postDTO));
    }
    return ResponseEntity.ok(homes);
  }
}
