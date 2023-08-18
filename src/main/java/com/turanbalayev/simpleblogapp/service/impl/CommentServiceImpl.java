package com.turanbalayev.simpleblogapp.service.impl;

import com.turanbalayev.simpleblogapp.entity.Comment;
import com.turanbalayev.simpleblogapp.entity.Post;
import com.turanbalayev.simpleblogapp.exception.BlogAPIException;
import com.turanbalayev.simpleblogapp.exception.ResourceNotFoundException;
import com.turanbalayev.simpleblogapp.payload.CommentDto;
import com.turanbalayev.simpleblogapp.repository.CommentRepository;
import com.turanbalayev.simpleblogapp.repository.PostRepository;
import com.turanbalayev.simpleblogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    @Override
    public void deleteComment(long postId, long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post.");
        }

        return mapToCommentDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post.");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment updatedComment = commentRepository.save(comment);
        return mapToCommentDTO(updatedComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToCommentDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = mapToCommentEntity(commentDto);

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToCommentDTO(newComment);
    }


    // convert comment entity to commentDto
    private CommentDto mapToCommentDTO(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);


/*        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());*/

        return commentDto;
    }

    //convert dto to comment entity
    private Comment mapToCommentEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto,Comment.class);


 /*       Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());*/

        return comment;
    }
}
