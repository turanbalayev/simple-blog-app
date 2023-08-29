package com.turanbalayev.simpleblogapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;
    private String email;
    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

}
