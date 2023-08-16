package com.turanbalayev.simpleblogapp.controller;


import com.turanbalayev.simpleblogapp.payload.PostDto;
import com.turanbalayev.simpleblogapp.payload.PostResponse;
import com.turanbalayev.simpleblogapp.service.PostService;
import com.turanbalayev.simpleblogapp.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable long id){
        return new ResponseEntity<>(postService.updatePostById(postDto,id),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully!",HttpStatus.OK);
    }
}
