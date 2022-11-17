package ourfirstblog.Blog.blog.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.User;

import javax.management.relation.Role;
import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);

    User saveNewUser(User user) throws RoleNotFoundException;

    List<User> getAllUsers();

    User getUserByID(long id);

    User saveUser(User user);
}