package org.example.task.Repository;

import org.example.task.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Post findByAuthor_Id(Long id);

    List<Post> findAllByAuthor_Id(Long id);

    Post findByIdAndAuthor_Id(Long id, Long authorId);
}
