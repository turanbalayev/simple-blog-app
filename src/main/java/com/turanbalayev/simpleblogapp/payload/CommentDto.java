package com.turanbalayev.simpleblogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;

    @NotNull(message = "Name should not be null.")
    @NotEmpty(message = "Name should not be empty.")
    private String name;


    @NotEmpty(message = "Email should not be null or empty.")
    @Email
    private String email;


    @NotEmpty
    @Size(min = 10, message = "Body should be minimum 10 characters.")
    private String body;

}
