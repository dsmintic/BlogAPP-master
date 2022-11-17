package ourfirstblog.Blog.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ourfirstblog.Blog.blog.Model.Comment;
import ourfirstblog.Blog.blog.Model.Post;
import ourfirstblog.Blog.blog.Model.User;
import ourfirstblog.Blog.blog.Service.CommentService;
import ourfirstblog.Blog.blog.Service.PostService;
import ourfirstblog.Blog.blog.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("comment")
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/comment/{id}")
    public String showComment(@PathVariable Long id,
                              Model model,
                              Principal principal) {

        String authUsername = "GuestUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<User> optUser = this.userService.findByUsername(authUsername);
        // find post by id
        Optional<Post> postOptional = this.postService.getById(id);
        // if both optionals is present set user and post to a new comment and put former in the model
        if (postOptional.isPresent() && optUser.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(postOptional.get());
            comment.setUser(optUser.get());
            model.addAttribute("comment", comment);
            System.err.println("GET comment/{id}: " + comment + "/" + id); // for testing debugging purposes
            return "commentForm";
        } else {
            System.err.println("Could not find a post by id: " + id + " or user by logged in username: " + authUsername); // for testing debugging purposes
            return "error";
        }
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/comment")
    public String validateComment(@Valid @ModelAttribute Comment comment,
                                  BindingResult bindingResult,
                                  SessionStatus sessionStatus) {

        System.err.println("POST comment: " + comment); // for testing debugging purposes

        if (bindingResult.hasErrors()) {

            System.err.println("Comment did not validate");
            return "commentForm";

        } else {

            this.commentService.save(comment);
            System.err.println("SAVE comment: " + comment); // for testing debugging purposes
            sessionStatus.setComplete();
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/deleteComment/{id}")
    public String deleteCommentById(@PathVariable Long id, HttpServletRequest request, Principal principal) {
        String authUsername = "GuestUser";

        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<Comment> optionalComment = this.commentService.getById(id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            if(authUsername.equals(comment.getUser().getUsername()) || request.isUserInRole("ADMIN")) {
                this.commentService.deleteCommentById(id);
                System.err.println("DELETED post: " + comment);
                return "redirect:/post/" + comment.getPost().getId();
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
