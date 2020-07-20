package com.technews.controller;

import com.technews.exception.NoMailException;
import com.technews.model.Post;
import com.technews.model.User;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> userList = repository.findAll();
        for (User u : userList) {
            List<Post> postList = u.getPosts();
            for (Post p : postList) {
                p.setVoteCount(voteRepository.countPostByPostId(p.getId()));
            }
        }

        return userList;
    }


    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        User returnUser = repository.getOne(id);
        List<Post> postList = returnUser.getPosts();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countPostByPostId(p.getId()));
        }

        return returnUser;
    }


    @PostMapping("/users/login")
    public User login(@RequestBody User user) throws NoMailException, Exception {

        User loginUser = repository.findUserByEmail(user.getEmail());

        try{
          if(loginUser.equals(null)) {

          }
        } catch (NullPointerException e) {
            throw new NoMailException("No user address found!");
        }

        return loginUser;
    }


    @PostMapping("/users")
    //@ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return repository.save(user);
    }


    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        User tempUser = repository.getOne(id);

        if(!tempUser.equals(null)) {
            user.setId(tempUser.getId());
            repository.save(user);
        }

        return user;

    }


    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }
}
