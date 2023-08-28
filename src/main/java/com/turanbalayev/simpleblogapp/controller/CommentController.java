package com.turanbalayev.simpleblogapp.controller;

import com.turanbalayev.simpleblogapp.payload.CommentDto;
import com.turanbalayev.simpleblogapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "CRUD Operations for Comment Resource")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Operation(
            summary = "Create a comment for a specific post",
            responses = @ApiResponse(
                    responseCode = "201",
                    description = "The request succeeded, and a new comment for a specific post was created as a result. "
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable long postId,
            @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Read all comments which belong to a specific post",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The request succeeded. The comments have been fetched and transmitted in the message body."
            )
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }


    @Operation(
            summary = "Read a comment which belongs to a specific post",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The request succeeded. The comment has been fetched and transmitted in the message body."
            )
    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable long postId,
            @PathVariable long commentId
    ){

        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a comment which belongs to a specific post",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The updated comment describing the result of the action is transmitted in the message body."
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable long postId,
            @PathVariable long commentId,
            @Valid @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto), HttpStatus.OK);
    }



    @Operation(
            summary = "Delete a comment which belongs to a specific post",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "The comment entity deleted successfully."
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable long postId,
            @PathVariable long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }





}
