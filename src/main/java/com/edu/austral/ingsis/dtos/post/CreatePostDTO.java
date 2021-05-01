package com.edu.austral.ingsis.dtos.post;

public class CreatePostDTO {

  private String text;
  private Long userId;
  private Long threadId;

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

  public Long getThreadId() {
    return threadId;
  }

  public void setThreadId(Long threadId) {
    this.threadId = threadId;
  }
}
