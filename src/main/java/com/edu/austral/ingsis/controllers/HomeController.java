package com.edu.austral.ingsis.controllers;

import com.edu.austral.ingsis.dtos.home.HomeDTO;
import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.PostService;
import com.edu.austral.ingsis.services.ThreadService;
import com.edu.austral.ingsis.utils.ObjectMapper;
import com.edu.austral.ingsis.utils.ObjectMapperImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.edu.austral.ingsis.utils.ConnectMicroservices.*;
import static com.edu.austral.ingsis.utils.SetUtilsToPostDTO.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class HomeController {

  private final PostService postService;
  private final ThreadService threadService;

  public HomeController(PostService postService, ThreadService threadService) {
    this.postService = postService;
    this.threadService = threadService;
  }

  @GetMapping("/home/posts")
  public ResponseEntity<List<HomeDTO>> getThread(@RequestHeader(name="Authorization") String token) {
    final String response = connectToUserMicroservice("/user/followed", HttpMethod.GET, token);
    final List<Long> longs = getLongs(response);
    final List<Post> posts = postService.getPostsOfFollowed(longs);
    final List<HomeDTO> homes = new ArrayList<>();
    for (Post post : sortByDate(posts)) {
      final Post first = postService.getFirstOfThread(post.getId());
      final PostDTO firstDTO = setDetailsToPost(first, threadService, token);
      final PostDTO postDTO = setLiked(setDetailsToPost(post, threadService, token), token);
      homes.add(new HomeDTO(firstDTO, postDTO));
    }
    return ResponseEntity.ok(homes);
  }

  private List<Long> getLongs(String json) {

    String[] jsons = json.substring(1, json.length()-1).split("},\\{");
    List<Long> longs = new ArrayList<>();
    for (String s: clean(jsons)) {
      longs.add(getId(s));
    }
    return longs;
  }

  private String[] clean(String[] jsons) {
    if (jsons.length <= 1) return jsons;
    jsons[0] = jsons[0].substring(1) + "}";
    if (jsons.length > 2) {
      for (int i = 1; i < jsons.length-1; i++) {
        jsons[i] = "{" + jsons[i] + "}";
      }
    }
    jsons[jsons.length-1] = "{" + jsons[jsons.length-1].substring(0, jsons[jsons.length-1].length()-1);
    return jsons;
  }

  private Long getId(String s) {
    return Long.valueOf(getFromJson(s, "id"));
  }
}
