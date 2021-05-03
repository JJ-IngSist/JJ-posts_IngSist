package com.edu.austral.ingsis.dtos.post;

public class CreatePostDTO {

  private String text;
  private Long user;
  private Long thread;

  public String getText() {
    return text;
  }

  public Long getUser() {
    return user;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setUser(Long user) {
    this.user = user;
  }

  public Long getThread() {
    return thread;
  }

  public void setThread(Long thread) {
    this.thread = thread;
  }
}
