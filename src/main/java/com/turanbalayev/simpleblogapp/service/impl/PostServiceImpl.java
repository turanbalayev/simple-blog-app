package com.turanbalayev.simpleblogapp.service.impl;

import com.turanbalayev.simpleblogapp.entity.Post;
import com.turanbalayev.simpleblogapp.payload.PostDto;
import com.turanbalayev.simpleblogapp.repository.PostRepository;
import com.turanbalayev.simpleblogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {


        // convert dto to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // dave post to database
        Post newPost = postRepository.save(post);

        // convert entity to dto
        PostDto postDtoResponse = new PostDto();
        postDtoResponse.setId(newPost.getId());
        postDtoResponse.setDescription(newPost.getDescription());
        postDtoResponse.setTitle(newPost.getTitle());
        postDtoResponse.setContent(newPost.getContent());

        return postDtoResponse;


    }
}
