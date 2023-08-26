package com.turanbalayev.simpleblogapp.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;


    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters.")
    private String title;

    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 10,message = "Post title should have at least 10 characters.")
    private String description;



    // content should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    private Long categoryID;

}
