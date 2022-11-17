package ourfirstblog.Blog.blog.Controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ourfirstblog.Blog.blog.Model.User;
import ourfirstblog.Blog.blog.Service.UserService;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@SessionAttributes("user")
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getRegisterForm(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "registerForm";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute User user,
                                  BindingResult bindingResult,
                                  SessionStatus sessionStatus) throws RoleNotFoundException {

        System.err.println("newUser: " + user);

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.username","Username is already registered try other one or go away");
            System.err.println("Username already taken error message");
        }

        if (bindingResult.hasErrors()) {
            System.err.println("New user did not validate");
            return "registerForm";
        }
        this.userService.saveNewUser(user);

        // Create Authentication token and login after registering new blog user
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.singleton(user.getAuthority()));
        System.err.println("AuthToken: " + auth);  // for testing debugging purposes
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.err.println("SecurityContext Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());  // for testing debugging purposes
        sessionStatus.setComplete();
        return "redirect:/homeBlogName";
    }
}
