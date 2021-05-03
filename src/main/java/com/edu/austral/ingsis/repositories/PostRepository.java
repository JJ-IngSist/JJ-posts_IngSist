package com.edu.austral.ingsis.repositories;

import com.edu.austral.ingsis.entities.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

  Optional<Post> findByUser(Long user_id);

  List<Post> findAll();

  @Query(value = "select * from posts p where p.id in (select tp.post_id from thread_post tp where tp.thread_id = ?1)", nativeQuery = true)
  List<Post> findAllByThread(Long id);

  @Query(value = "select * from posts p where p.user_id = ?1", nativeQuery = true)
  List<Post> getPostsOfUser(Long id);

  @Query(value = "select * from posts p order by p.likes desc limit ?1", nativeQuery = true)
  List<Post> getMostLiked(int size);
}
