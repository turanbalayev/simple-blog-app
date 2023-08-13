package com.turanbalayev.simpleblogapp.service;

import com.turanbalayev.simpleblogapp.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
