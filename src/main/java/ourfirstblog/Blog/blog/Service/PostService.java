package ourfirstblog.Blog.blog.Service;

import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Post;

import java.util.Collection;
import java.util.Optional;

@Service
public interface PostService {
    Optional<Post> getById(Long id);

    void delete(Post post);

    void save(Post post);

    Collection<Post> getAll();
}