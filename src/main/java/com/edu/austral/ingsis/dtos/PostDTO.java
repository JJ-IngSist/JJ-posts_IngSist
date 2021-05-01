package com.edu.austral.ingsis.dtos;

public class PostDTO {

  private Long id;
  private String text;
  private Long userId;

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Long getUserId() {
    return userId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
