package com.belvinard.blog_api.service.impl;

import com.belvinard.blog_api.dtos.ArticleDTO;
import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.exceptions.APIException;
import com.belvinard.blog_api.exceptions.ResourceNotFoundException;
import com.belvinard.blog_api.repository.ArticleRepository;
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
    public ArticleResponse getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new APIException("No articles create until now !");
        }

        List<ArticleDTO> articleDTOS = articles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .toList();

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

//    @Override
//    @Transactional
//    public Article createArticle(Article article) {
//        Article articleFromDb = articleRepository.findByTitle(article.getTitle());
//        if (articleFromDb != null) {
//            throw new ResourceNotFoundException("Article with the name " + article.getTitle() + " already exists");
//
//        }
//
//        Article savedArticle = articleRepository.save(article);
//        return savedArticle;
//    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Article", "article_id", articleId));
    }

    @Override
    @Transactional
    public Article updateArticle(Long articleId, Article articleDetails) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "article_id", articleId));

        article.setArticleId(articleId);

        Article savedArticle = articleRepository.save(article);
        return savedArticle;
    }

    @Override
    @Transactional
    public Article deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "article_id", articleId));
        articleRepository.delete(article);
        return article;
    }

}
