package com.edu.austral.ingsis.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "threads")
public class Thread {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Post> posts = new ArrayList<>();

  @Column(name = "first_id")
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
