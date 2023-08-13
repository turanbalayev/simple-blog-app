package com.turanbalayev.simpleblogapp.service.impl;

import com.turanbalayev.simpleblogapp.entity.Post;
import com.turanbalayev.simpleblogapp.exception.ResourceNotFoundException;
import com.turanbalayev.simpleblogapp.payload.PostDto;
import com.turanbalayev.simpleblogapp.repository.PostRepository;
import com.turanbalayev.simpleblogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        Post post = mapToPostEntity(postDto);

        // save post to database
        Post newPost = postRepository.save(post);

        // convert post entity to dto
        PostDto postDtoResponse = mapToDTO(newPost);
        return postDtoResponse;


    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);

    }


    // convert post entity to postDto
    private PostDto mapToDTO(Post post){

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToPostEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
