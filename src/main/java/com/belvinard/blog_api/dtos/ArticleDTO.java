package com.belvinard.blog_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Long articleId;
    private String title;
    private String content;
    private LocalDateTime publicationDate;
    private LocalDateTime lastUpdated;
}