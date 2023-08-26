package com.turanbalayev.simpleblogapp.service.impl;

import com.turanbalayev.simpleblogapp.entity.Category;
import com.turanbalayev.simpleblogapp.entity.Post;
import com.turanbalayev.simpleblogapp.exception.ResourceNotFoundException;
import com.turanbalayev.simpleblogapp.payload.PostDto;
import com.turanbalayev.simpleblogapp.payload.PostResponse;
import com.turanbalayev.simpleblogapp.repository.CategoryRepository;
import com.turanbalayev.simpleblogapp.repository.PostRepository;
import com.turanbalayev.simpleblogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {

        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryID()).orElseThrow(()->
                new ResourceNotFoundException("Category","id",postDto.getCategoryID()));



        // convert dto to entity
        Post post = mapToPostEntity(postDto);

        // set category
        post.setCategory(category);

        // save post to database
        Post newPost = postRepository.save(post);

        // convert post entity to dto
        PostDto postDtoResponse = mapTOPostDto(newPost);
        return postDtoResponse;


    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {



        // Create sort instance according to sortDir
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();



        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Send pageable to findAll to create a page instance
        Page<Post> posts = postRepository.findAll(pageable);

        // get content for Page
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapTOPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;


    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapTOPostDto(post);

    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Category category = categoryRepository.findById(postDto.getCategoryID()).orElseThrow(()->
                new ResourceNotFoundException("Category","id",postDto.getCategoryID()));


        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);

        return mapTOPostDto(updatedPost);


    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
    }


    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category","id", categoryId));

        return postRepository.findByCategoryId(categoryId)
                .stream()
                .map((post) -> mapper.map(post,PostDto.class))
                .collect(Collectors.toList());

    }

    // convert post entity to postDto
    private PostDto mapTOPostDto(Post post) {

        PostDto postDto = mapper.map(post,PostDto.class);


/*        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/

        return postDto;
    }

    //convert dto to post entity
    private Post mapToPostEntity(PostDto postDto) {

        Post post = mapper.map(postDto,Post.class);

/*        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        return post;
    }
}
