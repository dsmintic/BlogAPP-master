package ourfirstblog.Blog.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ourfirstblog.Blog.blog.Model.Post;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Collection<Post> findAllByOrderByCreationDateDesc();

    Collection<Post> findAllByOrderByCreationDateAsc();

    Optional<Post> findById(Long id);
}