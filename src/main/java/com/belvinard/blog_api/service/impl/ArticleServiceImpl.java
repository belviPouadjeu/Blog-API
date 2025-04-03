package com.belvinard.blog_api.service.impl;

import com.belvinard.blog_api.dtos.ArticleDTO;
import com.belvinard.blog_api.dtos.CommentDTO;
import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.exceptions.APIException;
import com.belvinard.blog_api.exceptions.ResourceNotFoundException;
import com.belvinard.blog_api.repositories.ArticleRepository;
import com.belvinard.blog_api.responses.ArticleResponse;
import com.belvinard.blog_api.service.ArticleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ArticleResponse getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new APIException("No articles created until now!");
        }

        List<ArticleDTO> articleDTOS = articles.stream().map(article -> {
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

            // ✅ Map comments manually
            List<CommentDTO> commentDTOS = article.getComments().stream()
                    .map(comment -> modelMapper.map(comment, CommentDTO.class))
                    .toList();
            articleDTO.setComments(commentDTOS);

            return articleDTO;
        }).toList();

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setContent(articleDTOS);
        return articleResponse;
    }


    @Override
    public ArticleDTO createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        Article article = modelMapper.map(articleDTO, Article.class);
        Article articleFromDb = articleRepository.findByTitle(article.getTitle());
        if (articleFromDb != null) {
            throw new ResourceNotFoundException("Article with the name " + article.getTitle() + " already exists");

        }


        Article savedArticle = articleRepository.save(article);
        return modelMapper.map(savedArticle, ArticleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDTO getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Article", "article_id", articleId));

        // Convert Article -> ArticleDTO
        return modelMapper.map(article, ArticleDTO.class);
    }

    @Override
    @Transactional
    public ArticleDTO patchArticle(Long articleId, ArticleDTO articleDTO) {
        Article existingArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "article_id", articleId));

        // Only update non-null fields from DTO to existing entity
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(articleDTO, existingArticle);

        // Ensure ID remains unchanged
        existingArticle.setArticleId(articleId);

        Article updatedArticle = articleRepository.save(existingArticle);
        return modelMapper.map(updatedArticle, ArticleDTO.class);
    }

    @Override
    @Transactional
    public ArticleDTO deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "article_id", articleId));

        // Convert to DTO before deletion for return
        ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

        articleRepository.delete(article);

        return articleDTO;  // Return DTO with deleted details
    }

}
