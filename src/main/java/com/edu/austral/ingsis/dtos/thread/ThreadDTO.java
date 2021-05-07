package com.edu.austral.ingsis.dtos.thread;

import com.edu.austral.ingsis.dtos.post.PostDTO;

import java.util.List;

public class ThreadDTO {

  private Long id;
  private List<PostDTO> posts;
  private Long firstPostId;

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

  public Long getFirstPostId() {
    return firstPostId;
  }

  public void setFirstPostId(Long firstPostId) {
    this.firstPostId = firstPostId;
  }
}
