package com.edu.austral.ingsis.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "text", nullable = false, length = 200)
  private String text;

  @Column(name = "user_id", nullable = false)
  private Long user;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "likes", nullable = false)
  private int likes;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getUser() {
    return user;
  }

  public void setUser(Long user) {
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
