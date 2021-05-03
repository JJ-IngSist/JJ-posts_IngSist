package com.edu.austral.ingsis.dtos.thread;

import com.edu.austral.ingsis.entities.Post;

import java.util.List;

public class ThreadDTO {

  private Long id;
  private List<Post> posts;
  private Long firstPostId;

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

  public Long getFirstPostId() {
    return firstPostId;
  }

  public void setFirstPostId(Long firstPostId) {
    this.firstPostId = firstPostId;
  }
}
