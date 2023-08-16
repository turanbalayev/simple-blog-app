package com.turanbalayev.simpleblogapp.repository;

import com.turanbalayev.simpleblogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
