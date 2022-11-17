package ourfirstblog.Blog.blog.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Post;
import ourfirstblog.Blog.blog.Repository.PostRepository;

import java.util.Collection;
import java.util.Optional;
@Service
public class PostServiceImplementation implements PostService{

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImplementation(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Collection<Post> getAll() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    public Collection<Post> getAllNO() {
        return postRepository.findAllByOrderByCreationDateAsc();
    }

    @Override
    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void delete(Post post) {
    postRepository.delete(post);
    }

    @Override
    public void save(Post post) {
    postRepository.save(post);
    }


}
