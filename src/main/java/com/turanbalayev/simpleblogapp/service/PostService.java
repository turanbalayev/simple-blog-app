package com.turanbalayev.simpleblogapp.service;

import com.turanbalayev.simpleblogapp.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long id);
}
