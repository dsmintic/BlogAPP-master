package ourfirstblog.Blog.blog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ourfirstblog.Blog.blog.Model.Authority;
import ourfirstblog.Blog.blog.Model.User;
import ourfirstblog.Blog.blog.Repository.AuthorityRepository;
import ourfirstblog.Blog.blog.Repository.UserRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    final BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username){return userRepository.findByUsername(username);
    }

    @Override
    public User saveNewUser(User user) throws RoleNotFoundException{
        System.err.println("SaveNewUser: " + user);

        user.setPassword(this.bcryptEncoder.encode(user.getPassword()));

        Optional<Authority> optionalAuthority = this.authorityRepository.findByAuthority("ROLE_USER");

        System.err.print("Optional Authority: " + optionalAuthority);

        if (optionalAuthority.isPresent()) {

            Authority authority = optionalAuthority.get();
            user.setAuthority(authority);
            System.err.println("blogUser after Roles: " + user);

            return this.userRepository.saveAndFlush(user);

        } else {
            throw new RoleNotFoundException("Default role not found for blog user with username " + user.getUsername());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByID(long id) {
        Optional<User> optional = this.userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            user = optional.get();
        }else{
            throw new RuntimeException("Employee with id: " + id + " is not found");
        }
        return user;
    }

    @Override
    public User saveUser(User user){
            user.setPassword(user.getPassword());
            return this.userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
    }
}
