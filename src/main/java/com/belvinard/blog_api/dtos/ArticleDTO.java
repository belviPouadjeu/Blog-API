package com.belvinard.blog_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDTO {
    @Schema(hidden = true) // Hides articleId in the request schema
    private Long articleId;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 30, message = "Title must be 5-30 characters")
    @Schema(description = "Title of the article", example = "Introduction to HTML5")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 200, message = "Content must be at least 10 characters")
    @Schema(description = "Content of the article",
            example = "HTML5 is the latest version of the HyperText Markup Language used to structure web pages.")
    private String content;

    @Schema(hidden = true) // Hide publicationDate in request schema
    private LocalDateTime publicationDate;

    @Schema(hidden = true) // Hide lastUpdated in request schema
    private LocalDateTime lastUpdated;
    private List<CommentDTO> comments;

    public ArticleDTO() {
    }

    public ArticleDTO(Long articleId, String title, String content,
                      LocalDateTime publicationDate, LocalDateTime lastUpdated,
                      List<CommentDTO> comments) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.publicationDate = publicationDate;
        this.lastUpdated = lastUpdated;
        this.comments = comments;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }




    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}