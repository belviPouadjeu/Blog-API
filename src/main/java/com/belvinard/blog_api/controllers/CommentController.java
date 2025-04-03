package com.belvinard.blog_api.controllers;

import com.belvinard.blog_api.dtos.CommentDTO;
import com.belvinard.blog_api.responses.MyErrorResponses;
import com.belvinard.blog_api.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Add Comments", description = "Endpoints for adding article to comments to a given article")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Add a comment to an article")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comment added successfully",
                content = @Content(schema = @Schema(implementation = CommentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Article not found",
                content = @Content(schema = @Schema(implementation = MyErrorResponses.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = MyErrorResponses.class)))
    })
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long articleId,
            @Valid @RequestBody CommentDTO commentDTO) {

        CommentDTO createdComment = commentService.addComment(articleId, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }
}
