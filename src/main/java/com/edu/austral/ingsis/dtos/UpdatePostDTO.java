package com.edu.austral.ingsis.dtos;

public class UpdatePostDTO {

  private String text;
  private Long userId;

  public UpdatePostDTO(String text, Long userId) {
    this.text = text;
    this.userId = userId;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
