package com.belvinard.blog_api.controllers;

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
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

    // ==================== GET ALL ARTICLE
    @Operation(
            summary = "Retrieve all articles",
            description = """
        Returns the complete list of articles stored in the database.
        This API is accessible without authentication.
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of articles successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class),
                            examples = @ExampleObject(value = """
        {
            "content": [
                {
                    "articleId": 1,
                    "title": "Introduction to HTML5",
                    "content": "HTML5 is the latest version of the HyperText Markup Language used to structure web pages.",
                    "publicationDate": "2025-04-03T15:08:33.492405",
                    "lastUpdated": "2025-04-03T15:08:33.491407"
                },
                {
                    "articleId": 2,
                    "title": "CSS Grid vs Flexbox",
                    "content": "Learn the differences and use cases for CSS Grid and Flexbox in modern web design.",
                    "publicationDate": "2025-04-03T15:11:41.467586",
                    "lastUpdated": "2025-04-03T15:11:41.467586"
                }
            ]
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No articles found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "NOT_FOUND",
            "message": "No articles available"
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "INTERNAL_SERVER_ERROR",
            "message": "An error occurred while retrieving the articles"
        }
        """)
                    )
            )
    })

    @GetMapping
    public ResponseEntity<ArticleResponse> getAllArticles() {
        ArticleResponse articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    // ==================== CREATEARTICLE
    @Operation(
            summary = "Create a new article",
            description = """
        Adds a new article to the blog.
        The request must include a valid article object with a title and content.
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleDTO.class),
                            examples = @ExampleObject(value = """
        {
            "title": "The Impact of Climate Change",
            "content": "Rising temperatures and extreme weather events highlight the urgency..."
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input - missing or incorrect fields",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "BAD_REQUEST",
            "message": "Title is required"
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "INTERNAL_SERVER_ERROR",
            "message": "An unexpected error occurred while creating the article"
        }
        """)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(
            @RequestBody @Valid ArticleDTO articleDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    // ==================== GET ARTICLE BY ID
    @Operation(
            summary = "Get an article by ID",
            description = """
        Retrieves a single article based on the provided ID.
        The article ID must be valid and exist in the database.
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleDTO.class),
                            examples = @ExampleObject(value = """
        {
            "articleId": 1,
             "title": "Introduction to HTML5",
             "content": "HTML5 is the latest version of the HyperText Markup Language used to structure web pages.",
             "publicationDate": "2025-04-03T15:08:33.492405",
             "lastUpdated": "2025-04-03T15:08:33.491407"
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
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "INTERNAL_SERVER_ERROR",
            "message": "An unexpected error occurred while retrieving the article"
        }
        """)
                    )
            )
    })
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> getArticleById(
            @Parameter(description = "ID of the article to be retrieved", required = true)
            @PathVariable Long articleId) {
        ArticleDTO articleDTO = articleService.getArticleById(articleId);
        return ResponseEntity.ok(articleDTO);
    }

    // ==================== UPDATE ARTICLE
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

    // ==================== DELETE ARTICLE
    @Operation(
            summary = "Delete an article",
            description = """
        Deletes a blog article by its ID.
        If the article exists, it will be removed permanently.
        If the article ID does not exist, an error is returned.
    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Article deleted successfully",
                    content = @Content(
                            examples = @ExampleObject(value = """
       
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
            "message": "No article found with ID 15"
        }
        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MyErrorResponses.class),
                            examples = @ExampleObject(value = """
        {
            "code": "INTERNAL_SERVER_ERROR",
            "message": "An unexpected error occurred while deleting the article"
        }
        """)
                    )
            )
    })
    @DeleteMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> deleteArticle(
            @Parameter(description = "ID of the article to be deleted", required = true)
            @PathVariable Long articleId) {

        ArticleDTO deletedArticle = articleService.deleteArticle(articleId);
        return ResponseEntity.ok(deletedArticle);
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






}