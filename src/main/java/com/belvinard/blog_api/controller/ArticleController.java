package com.belvinard.blog_api.controller;

import com.belvinard.blog_api.dtos.ArticleDTO;
import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.exceptions.APIException;
import com.belvinard.blog_api.exceptions.ResourceNotFoundException;
import com.belvinard.blog_api.responses.ArticleResponse;
import com.belvinard.blog_api.responses.MyErrorResponses;
import com.belvinard.blog_api.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
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
@Tag(name = "Article Management", description = "Endpoints for managing blog articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(summary = "Get all articles", description = "Returns a list of all articles")
    @ApiResponse(responseCode = "200", description = "List of articles",
            content = @Content(schema = @Schema(implementation = Article.class)))
    @GetMapping
    public ResponseEntity<ArticleResponse> getAllArticles() {
        ArticleResponse articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @Operation(summary = "Create a new article", description = "Creates a new blog article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = MyErrorResponses.class)))
    })
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(
            @RequestBody @Valid ArticleDTO articleDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @Operation(summary = "Get article by ID", description = "Returns a single article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article found",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = @Content(schema = @Schema(implementation = MyErrorResponses.class)))
    })
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> getArticleById(
            @Parameter(description = "ID of the article to be retrieved", required = true)
            @PathVariable Long articleId) {
        ArticleDTO articleDTO = articleService.getArticleById(articleId);
        return ResponseEntity.ok(articleDTO);
    }


    @Operation(summary = "Update an article", description = "Updates an existing article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article updated successfully",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = MyErrorResponses.class))),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = @Content(schema = @Schema(implementation = MyErrorResponses.class)))
    })
    @PatchMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> patchArticle(
            @PathVariable Long articleId,
            @Valid @RequestBody ArticleDTO articleDTO) {

        ArticleDTO updatedArticle = articleService.patchArticle(articleId, articleDTO);
        return ResponseEntity.ok(updatedArticle);
    }

//
//    @Operation(summary = "Delete an article", description = "Deletes an article by its ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Article deleted successfully"),
//            @ApiResponse(responseCode = "404", description = "Article not found",
//                    content = @Content(schema = @Schema(implementation = MyErrorResponses.class)))
//    })
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteArticle(
//            @Parameter(description = "ID of the article to be deleted", required = true)
//            @PathVariable Long id) {
//        articleService.deleteArticle(id);
//        return ResponseEntity.noContent().build();
//    }
//


    @Operation(hidden = true)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyErrorResponses> handleResourceNotFoundException(ResourceNotFoundException ex) {
        MyErrorResponses errorResponse = new MyErrorResponses("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<MyErrorResponses> myAPIException(APIException ex) {
        MyErrorResponses errorResponse = new MyErrorResponses("BAD_REQUEST", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<MyErrorResponses> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        MyErrorResponses errorResponse = new MyErrorResponses("VALIDATION_ERROR", "Validation failed");
//        errorResponse.setErrors(errors); // Add this field to your MyErrorResponses class
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }






}