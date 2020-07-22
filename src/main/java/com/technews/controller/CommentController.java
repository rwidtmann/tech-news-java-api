package com.technews.controller;

import com.technews.exception.NoEmailException;
import com.technews.model.Comment;
import com.technews.model.User;
import com.technews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentRepository repository;

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return repository.findAll();
    }


    @GetMapping("/comments/{id}")
    public Comment getComment(@PathVariable int id) {
        return repository.getOne(id);
    }


    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment, HttpServletRequest request) throws NoEmailException, Exception {
        User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");

        try{
            if(sessionUser.equals(null)) {

            }
        } catch (NullPointerException e) {
            throw new NoEmailException("User is not logged in");
        }


        comment.setUserId(sessionUser.getId());
        return repository.save(comment);
    }


    //    @PutMapping("/updateComment")
//    public Comment updateComment(@RequestBody Comment comment) {
//        return repository.save(comment);
//    }
//
//
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable int id) {
        repository.deleteById(id);
    }
}
