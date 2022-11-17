package ourfirstblog.Blog.blog.Service;

import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Comment;
import ourfirstblog.Blog.blog.Model.Post;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    void saveComment(Comment comment);
    void deleteCommentById (long id);

    void save(Comment comment);

    Optional<Comment> getById(Long id);
}