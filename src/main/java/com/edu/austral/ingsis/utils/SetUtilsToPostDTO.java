package com.edu.austral.ingsis.utils;

import com.edu.austral.ingsis.dtos.post.PostDTO;
import com.edu.austral.ingsis.entities.Post;
import com.edu.austral.ingsis.services.ThreadService;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.edu.austral.ingsis.utils.ConnectMicroservices.connectToUserMicroservice;
import static com.edu.austral.ingsis.utils.ConnectMicroservices.setUserDetails;

public class SetUtilsToPostDTO {

  private final static ObjectMapper objectMapper = new ObjectMapperImpl();

  public static PostDTO setThreadId(PostDTO postDTO, ThreadService service) {
    postDTO.setThreadId(service.getByPostId(postDTO.getId()).getId());
    return postDTO;
  }

  public static PostDTO setLiked(PostDTO postDTO, String token) {
    if (!token.isEmpty()) {
      final String response = connectToUserMicroservice("/logged/" + postDTO.getId() + "/liked", HttpMethod.GET, token);
      postDTO.setLiked(Boolean.parseBoolean(response));
    }
    return postDTO;
  }

  public static List<Post> sortByDate(List<Post> posts) {
    posts.sort(Comparator.comparing(Post::getDate).reversed());
    return posts;
  }

  public static List<PostDTO> setDetailsToPosts(List<Post> notSorted, ThreadService service, String token) {
    List<Post> posts = sortByDate(notSorted);
    List<PostDTO> updatedPostDTOS = new ArrayList<>();
    for(Post p : posts) {
      updatedPostDTOS.add(setDetailsToPost(p, service, token));
    }
    return updatedPostDTOS;
  }

  public static PostDTO setDetailsToPost(Post post, ThreadService service, String token) {
    PostDTO dto = objectMapper.map(post, PostDTO.class);
    return setLiked(setThreadId(setUserDetails(connectToUserMicroservice("/user/" + dto.getUser(), HttpMethod.GET, ""), dto), service), token);
  }
}
