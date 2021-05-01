package com.edu.austral.ingsis.repositories;


import com.edu.austral.ingsis.entities.Thread;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {

  List<Thread> findAll();

  @Query(value = "select * from thread t where t.id in (select tp.thread_id from thread_post tp where tp.post_id = ?1)", nativeQuery = true)
  Thread findThreadOfPostById(Long id);
}
