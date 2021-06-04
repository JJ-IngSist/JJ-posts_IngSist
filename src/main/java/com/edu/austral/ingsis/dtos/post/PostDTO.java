package com.edu.austral.ingsis.dtos.post;

import java.time.LocalDateTime;

public class PostDTO {

  private Long id;
  private String text;
  private Long user;
  private String name;
  private String username;
  private String email;
  private Long threadId;
  private LocalDateTime date;
  private int likes;
  private boolean liked;

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Long getUser() {
    return user;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setUser(Long user) {
    this.user = user;
  }

  public Long getThreadId() {
    return threadId;
  }

  public void setThreadId(Long threadId) {
    this.threadId = threadId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }
}
