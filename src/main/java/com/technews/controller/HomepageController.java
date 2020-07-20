package com.technews.controller;

import com.technews.model.Comment;
import com.technews.model.Post;
import com.technews.model.User;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HomepageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    VoteRepository voteRepository;


    @GetMapping("/homePage/posts")
    public String getAllPosts(Model model) {
        List<Post> postList = postRepository.findAll();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countPostByPostId(p.getId()));
            User user = userRepository.getOne(p.getUserId());
            p.setUserName(user.getUsername());
        }

        model.addAttribute("postList", postList);

        return "homepage-main";
    }


    @GetMapping("/login")
    public String login() {
        return "login-main";
    }


    @GetMapping("/mustache")
    public String mustacheDemo(Model model) {
        //model.addAttribute("user", new User());
        model.addAttribute("message", "This is the test");
        model.addAttribute("message2", "Changing message 2");

        return "maybe-main";
    }

    @GetMapping("/homepage")
    public String getHomepage(Model model) {
        Post post = new Post();
        post.setId(55);
        post.setPostUrl("https://handlebarsjs.com/guide/");
        post.setTitle("Handlebars Docs");
        post.setVoteCount(10);
        post.setComments(Arrays.asList(new Comment(), new Comment()));

        User user = new User();
        user.setUsername("test_user");

        model.addAttribute("message", "This is the test");
        model.addAttribute("message2", "Message 2 from homepage endpoint");
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        return "homepage-main";
    }

//    @GetMapping("/homeRoutes")
//    public String greetingForm(Model model) {
//        //model.addAttribute("user", new User());
//        return "homepage";
//    }

//    @PostMapping("/homeRoutes")
//    public String greetingSubmit(@ModelAttribute User user) {
//        return "result";
//    }
}
