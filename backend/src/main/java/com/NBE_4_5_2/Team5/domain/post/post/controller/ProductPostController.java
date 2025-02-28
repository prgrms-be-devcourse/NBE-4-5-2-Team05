package com.NBE_4_5_2.Team5.domain.post.post.controller;

import com.NBE_4_5_2.Team5.domain.post.post.service.ProductPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ProductPostController {
    private final ProductPostService productPostService;

//    @PostMapping
//    public void createPost() {
//
//    }
//
//    @GetMapping
//    public void getPosts() {
//
//    }
//
//    @GetMapping("/{id}")
//    public void getPost() {
//
//    }
//
//    @PutMapping("/{id}")
//    public void modify() {
//
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete() {
//
//    }

}
