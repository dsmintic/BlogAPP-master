package ourfirstblog.Blog.blog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Comment;
import ourfirstblog.Blog.blog.Model.Post;
import ourfirstblog.Blog.blog.Repository.CommentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService{

    private final CommentRepository commentRepository;
    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Collection<Comment> getAllDesc() {
        return commentRepository.findAllByOrderByCreationDateDesc();
    }

    public Collection<Comment> getAllAsc() {
        return commentRepository.findAllByOrderByCreationDateAsc();
    }

    @Override
    public void saveComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(long id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return commentRepository.findById(id);
    }
}
