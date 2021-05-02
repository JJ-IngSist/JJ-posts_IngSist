package com.edu.austral.ingsis.dtos.post;

import java.time.LocalDate;

public class PostDTO {

  private Long id;
  private String text;
  private Long userId;
  private Long threadId;
  private LocalDate date;
  private int likes;

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

  public Long getThreadId() {
    return threadId;
  }

  public void setThreadId(Long threadId) {
    this.threadId = threadId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }
}
