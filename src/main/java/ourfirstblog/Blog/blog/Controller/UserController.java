package ourfirstblog.Blog.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourfirstblog.Blog.blog.Model.User;
import ourfirstblog.Blog.blog.Service.AuthorityService;
import ourfirstblog.Blog.blog.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

 private UserService userService;
 private AuthorityService authorityService;

    @Autowired
        public UserController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }


    @GetMapping("/users")
    public String showUsers(HttpServletRequest request,
                            Model model){

        if (request.isUserInRole("ADMIN")){

            model.addAttribute("user", userService.getAllUsers());
            return "users";
        }
        else{
            return "redirect:/";
        }
    }

    @GetMapping("/userUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id,
                                    HttpServletRequest request,
                                    Model model){
        if (request.isUserInRole("ADMIN")){

            User user = userService.getUserByID(id);
            model.addAttribute("user", user);
            model.addAttribute("authority", authorityService.getAllAuthorities());

        return "updateForm";

        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/saveUser")
    public String saveEmployee(@Valid @ModelAttribute User user,
                               HttpServletRequest request,
                               Model model){

        model.addAttribute("authority", authorityService.getAllAuthorities());

        if (request.isUserInRole("ADMIN")){

        userService.saveUser(user);
        return "redirect:/users";

    }else{
            return "redirect:/";
        }
    }
}
