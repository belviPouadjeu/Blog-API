package com.belvinard.blog_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")  // Optional (if you want to explicitly name the table)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false, length = 30)
    @Size(min = 5, max = 30, message = "Title must be 5-30 characters")
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Column(nullable = false, length = 2000)
    @Size(min = 10, message = "Content must be at least 10 characters")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @CreationTimestamp
    @Column(name = "publication_date", nullable = false, updatable = false)
    private LocalDateTime publicationDate;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Prevents infinite recursion in JSON responses
    private List<Comment> comments = new ArrayList<>();
}