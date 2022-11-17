package ourfirstblog.Blog.blog.Service;

import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Authority;
import ourfirstblog.Blog.blog.Model.Comment;

import java.util.List;
import java.util.Optional;

@Service
public interface AuthorityService {
 List<Authority> getAllAuthorities();
}