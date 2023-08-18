package com.turanbalayev.simpleblogapp.payload;

import com.turanbalayev.simpleblogapp.entity.Post;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    private String body;
    private String email;
    private String name;

}
