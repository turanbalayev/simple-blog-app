package com.turanbalayev.simpleblogapp.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Schema(
        description = "PostDto Model Information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;

    @Schema(description = "Blog Post Title")
    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters.")
    private String title;

    // title should not be null or empty
    // title should have at least 2 characters
    @Schema(description = "Blog post description")
    @NotEmpty
    @Size(min = 10, message = "Post title should have at least 10 characters.")
    private String description;


    @Schema(description = "Blog Post Content")
    // content should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;


    @Schema(description = "Blog Post Category")
    private Long categoryID;

}
