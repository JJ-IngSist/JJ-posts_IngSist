package com.edu.austral.ingsis.dtos.thread;

import com.edu.austral.ingsis.dtos.post.PostDTO;

import java.util.List;

public class ThreadDTO {

  private Long id;
  private List<PostDTO> posts;
  private PostDTO firstPost;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<PostDTO> getPosts() {
    return posts;
  }

  public void setPosts(List<PostDTO> posts) {
    this.posts = posts;
  }

  public PostDTO getFirstPost() {
    return firstPost;
  }

  public void setFirstPost(PostDTO firstPost) {
    this.firstPost = firstPost;
  }
}
