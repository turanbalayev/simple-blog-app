package com.turanbalayev.simpleblogapp.controller;


import com.turanbalayev.simpleblogapp.payload.PostDto;
import com.turanbalayev.simpleblogapp.payload.PostResponse;
import com.turanbalayev.simpleblogapp.service.PostService;
import com.turanbalayev.simpleblogapp.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD Operations for Post Resource")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(
            summary = "Create a new post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "The request succeeded, and a new post was created as a result. "
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Read all posts with pagination and sorting"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The request succeeded. The posts have been fetched and transmitted in the message body. "

    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    @Operation(
            summary = "Read a single post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The request succeeded. The post hase been fetched and transmitted in the message body. "

    )
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }


    @Operation(
            summary = "Update a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The updated post describing the result of the action is transmitted in the message body."

    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable("postId") long id) {
        return new ResponseEntity<>(postService.updatePostById(postDto, id), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The post deleted successfully."

    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable("postId") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully!", HttpStatus.OK);
    }

    @Operation(
            summary = "Read all posts belongs to the given category",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "The posts which belong to the given category have been fetched and transmitted in the message body."
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category is not found with the given id."

                    )}
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable long categoryId) {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }
}
