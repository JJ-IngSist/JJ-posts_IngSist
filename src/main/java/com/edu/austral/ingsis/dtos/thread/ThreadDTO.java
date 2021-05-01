package com.edu.austral.ingsis.dtos.thread;

import com.edu.austral.ingsis.entities.Post;

import java.util.List;

public class ThreadDTO {

  private Long id;
  private List<Post> posts;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }
}
