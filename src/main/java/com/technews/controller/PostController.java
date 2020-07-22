package com.technews.controller;

import com.technews.model.Post;
import com.technews.model.User;
import com.technews.model.Vote;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts(Model model) {
        List<Post> postList = repository.findAll();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countPostByPostId(p.getId()));
        }
        return postList;
    }


    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable Integer id) {
        Post returnPost = repository.getOne(id);
        User tempUser = userRepository.getOne(returnPost.getUserId());
        returnPost.setUserName(tempUser.getUsername());
        returnPost.setVoteCount(voteRepository.countPostByPostId(returnPost.getId()));

        return returnPost;
    }


    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post) {
        return repository.save(post);
    }


    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        Post tempPost = repository.getOne(id);
        tempPost.setTitle(post.getTitle());
        return repository.save(tempPost);
    }


    @PutMapping("/posts/upvote")
    public Post addVote(@RequestBody Vote vote, HttpServletRequest request) {
        Post returnPost = null;

        User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
        if(!sessionUser.equals(null)) {
            vote.setUserId(sessionUser.getId());
            voteRepository.save(vote);

            returnPost = repository.getOne(vote.getPostId());
            returnPost.setVoteCount(voteRepository.countPostByPostId(vote.getPostId()));
        }
        

        return returnPost;
    }


    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        repository.deleteById(id);
    }
}
