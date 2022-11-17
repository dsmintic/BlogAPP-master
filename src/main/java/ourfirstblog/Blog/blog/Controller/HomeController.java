package ourfirstblog.Blog.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ourfirstblog.Blog.blog.Model.Post;
import ourfirstblog.Blog.blog.Service.PostService;

import java.util.Collection;

@Controller
public class HomeController {

    private PostService postService;

    @Autowired

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/homeBlogName")
    public String displayAllPosts(Model model) {

        Collection<Post> posts = this.postService.getAll();
        model.addAttribute("posts", posts);

        return "home";
    }

    @GetMapping("/")
    public String returnHome() {
        return "redirect:/homeBlogName";
    }

}
