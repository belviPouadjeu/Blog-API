package com.belvinard.blog_api.controllers;

import com.belvinard.blog_api.dtos.CommentDTO;
import com.belvinard.blog_api.exceptions.ResourceNotFoundException;
import com.belvinard.blog_api.responses.MyErrorResponses;
import com.belvinard.blog_api.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Add Comments", description = "Endpoints for adding comments to a specific article, enabling user interaction and feedback.")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Add a comment to an article",
            description = """
        Adds a comment to a specified article.
        If the article exists, the comment will be added successfully.
        If the article ID does not exist, an error is returned.
        If the input is invalid, an error response is returned.
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class),
                            examples = @ExampleObject(value = """
        {
            "text": "Great article! Very informative.",
            "createdAt": "2024-04-04T12:00:00Z"
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "NOT_FOUND",
            "message": "No article found with ID 10"
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "BAD_REQUEST",
            "message": "Comment text cannot be empty"
        }
        """)
                    )
            )
    })
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long articleId,
            @Valid @RequestBody CommentDTO commentDTO) {

        CommentDTO createdComment = commentService.addComment(articleId, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @Operation(hidden = true)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyErrorResponses> handleResourceNotFoundException(ResourceNotFoundException ex) {
        MyErrorResponses errorResponse = new MyErrorResponses("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // ✅ Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorResponses> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());  // Collect field errors
        }

        MyErrorResponses errorResponse = new MyErrorResponses(
                "BAD_REQUEST",
                "Validation failed. Please correct the errors.",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
