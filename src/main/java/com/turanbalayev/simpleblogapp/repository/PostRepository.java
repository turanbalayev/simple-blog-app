package com.turanbalayev.simpleblogapp.repository;

import com.turanbalayev.simpleblogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByCategoryId(Long categoryId);

}
