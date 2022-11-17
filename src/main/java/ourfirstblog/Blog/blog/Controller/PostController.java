package ourfirstblog.Blog.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ourfirstblog.Blog.blog.Model.Post;
import ourfirstblog.Blog.blog.Model.User;
import ourfirstblog.Blog.blog.Service.PostService;
import ourfirstblog.Blog.blog.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("post")
public class PostController {
    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id,
                          Model model,
                          Principal principal) {
        String authUsername = "GuestUser";

        if(principal == null){
            Optional<Post> optionalPostTemp = this.postService.getById(id);
            if (optionalPostTemp.isPresent()) {
                Post post = optionalPostTemp.get();
                model.addAttribute("post", post);
                return "post";
            }
        }

            if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<Post> optionalPost = this.postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("owner", true);
            }
            model.addAttribute("userLogIn", principal.getName());
            return "post";
        } else {
            return "404";
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/createNewPost")
    public String createNewPost(Model model,
                                Principal principal) {

        String authUsername = "GuestUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<User> optUser = this.userService.findByUsername(authUsername);

        if (optUser.isPresent()) {
            Post post = new Post();
            post.setUser(optUser.get());
            model.addAttribute("post", post);
            return "postForm";
        } else {
            return "error";
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/createNewPost")
    public String createNewPost(@Valid @ModelAttribute Post post,
                                BindingResult bindingResult,
                                SessionStatus sessionStatus) {
        System.err.println("POST post: " + post); // for testing debugging purposes

        if (bindingResult.hasErrors()) {
            System.err.println("Post did not validate");
            return "postForm";
        }
        // Save post if all good
        this.postService.save(post);
        System.err.println("SAVE post: " + post); // for testing debugging purposes
        sessionStatus.setComplete();
        return "redirect:/post/" + post.getId();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("editPost/{id}")
    public String editPost(@PathVariable Long id,
                           Model model,
                           HttpServletRequest request,
                           Principal principal) {
        String authUsername = "GuestUser";

        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Post> optionalPost = this.postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if(authUsername.equals(post.getUser().getUsername()) || request.isUserInRole("ADMIN")) {
                model.addAttribute("post", post);
                System.err.println("EDIT post: " + post); // for testing debugging purposes
                return "postForm";
            } else {
                System.err.println("Current User has no permissions to edit anything on post by id: " + id); // for testing debugging purposes
                return "403";
            }
        } else {
            System.err.println("Could not find a post by id: " + id); // for testing debugging purposes
            return "error";
        }
    }



    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id,HttpServletRequest request, Principal principal) {
        String authUsername = "GuestUser";

        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Post> optionalPost = this.postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if(authUsername.equals(post.getUser().getUsername()) || request.isUserInRole("ADMIN")) {
                this.postService.delete(post);
                System.err.println("DELETED post: " + post);
                return "redirect:/";
            } else {
                System.err.println("Current User has no permissions to edit anything on post by id: " + id); // for testing debugging purposes
                return "403";
            }

        } else {
            System.err.println("Could not find a post by id: " + id); // for testing debugging purposes
            return "error";
        }
    }

}
