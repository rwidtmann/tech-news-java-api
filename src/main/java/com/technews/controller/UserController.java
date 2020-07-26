package com.technews.controller;

import com.technews.exception.NoEmailException;
import com.technews.model.Post;
import com.technews.model.User;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
//@RequestMapping("/api")
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
    public User login(@RequestBody User user, HttpServletRequest request) throws NoEmailException, Exception {

        User sessionUser = repository.findUserByEmail(user.getEmail());

        try{
            if(sessionUser.equals(null)) {

            }
        } catch (NullPointerException e) {

            throw new NoEmailException("No user address found!");
        }

        sessionUser.setLoggedIn(true);
        request.getSession().setAttribute("SESSION_USER", sessionUser);

        return sessionUser;


//        List<User> userList = repository.findAll();
//        for (User u : userList) {
//            List<Post> postList = u.getPosts();
//            for (Post p : postList) {
//                p.setVoteCount(voteRepository.countPostByPostId(p.getId()));
//            }
//        }
//
//        return userList;


//        String returnValue = "";
//
//        if(request.getSession(false) != null) {
//            User sessionUser = repository.findUserByEmail(user.getEmail());
//            request.getSession().setAttribute("SESSION_USER", sessionUser);
//            returnValue = "dashboard-main";
//        } else {
//            returnValue = "login-main";
//        }
//
//        return returnValue;
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


//    private boolean isLoggedIn(User user, HttpServletRequest request) {
////        String sessionId = request.getSession().getId();
////        System.out.println("Session Id is: " + sessionId);
////
////        request.getSession().getId()
//
//        String sessionAttribute = (String) request.getSession().getAttribute(user.getEmail());
//        boolean result = false;
//        if(sessionAttribute.equals(user.getEmail())) {
//            System.out.println("User is logged in...");
//            result = true;
//        } else {
//            System.out.println("User is NOT logged in...");
//            result = false;
//        }
//
//        return result;
//    }

}
