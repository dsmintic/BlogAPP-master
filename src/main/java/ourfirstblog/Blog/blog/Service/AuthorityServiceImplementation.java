package ourfirstblog.Blog.blog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Authority;
import ourfirstblog.Blog.blog.Model.Comment;
import ourfirstblog.Blog.blog.Repository.AuthorityRepository;
import ourfirstblog.Blog.blog.Repository.CommentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImplementation implements AuthorityService {

    private AuthorityRepository authorityRepository;
    @Autowired
    public AuthorityServiceImplementation(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }
}
