package com.technews.controller;

import com.technews.model.Comment;
import com.technews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Comment getComment(@PathVariable Integer id) {
        return repository.getOne(id);
    }


    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
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
