package com.edu.austral.ingsis.dtos;

public class CreatePostDTO {

  private String text;
  private Long userId;

  public String getText() {
    return text;
  }

  public Long getUserId() {
    return userId;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
