package ourfirstblog.Blog.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ourfirstblog.Blog.blog.Model.Comment;
import ourfirstblog.Blog.blog.Model.Post;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findAllByOrderByCreationDateDesc();

    Collection<Comment> findAllByOrderByCreationDateAsc();

    Optional<Comment> findById(long id);
}