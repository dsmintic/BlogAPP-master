package ourfirstblog.Blog.blog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Principal principal){
        if(principal != null){
            return "redirect:/homeBlogName";
        }else {
            return "login";
        }
    }
}
