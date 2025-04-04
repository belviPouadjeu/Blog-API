package com.belvinard.blog_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Data transfer object for adding a comment to an article")
public class CommentDTO {

    @NotBlank(message = "Comment text cannot be blank")
    @Size(min = 10, max = 400, message = "Comment must be 10-400 characters")
    @Schema(description = "The text of the comment",
            example = "Great article! Very informative.", minLength = 10,
            maxLength = 400)
    @JsonProperty("text")
    private String text;

    @Schema(hidden = true)
    private LocalDateTime createdAt;

    public CommentDTO() {
    }

    public CommentDTO(String text, LocalDateTime createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}