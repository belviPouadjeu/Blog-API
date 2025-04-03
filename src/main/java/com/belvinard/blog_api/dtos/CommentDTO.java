package com.belvinard.blog_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Schema(description = "Data transfer object for adding a comment to an article")
public class CommentDTO {

    @NotBlank(message = "Comment text cannot be blank")
    @Size(min = 10, max = 400, message = "Comment must be 10-400 characters")
    @Schema(description = "The text of the comment",
            example = "Great article! Very informative.", minLength = 10,
            maxLength = 400)
    @JsonProperty("text")
    private String text;

    public CommentDTO(String text) {
        this.text = text;
    }

    public CommentDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}