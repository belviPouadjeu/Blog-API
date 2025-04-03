package com.belvinard.blog_api.service.impl;

import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.exceptions.ResourceNotFoundException;
import com.belvinard.blog_api.repository.ArticleRepository;
import com.belvinard.blog_api.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public Article createArticle(Article article) {
        Article articleFromDb = articleRepository.findByTitle(article.getTitle());
        if (articleFromDb != null) {
            throw new ResourceNotFoundException("Article with the name " + article.getTitle() + " already exists");

        }

        Article savedArticle = articleRepository.save(article);
        return savedArticle;
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Article", "article_id", articleId));
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new ResourceNotFoundException("No articles create until now !");
        }
        return articles;
    }

    @Override
    @Transactional
    public Article updateArticle(Long articleId, Article articleDetails) {
        Article article = getArticleById(articleId);
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Long articleId) {
        Article article = getArticleById(articleId);
        articleRepository.delete(article);
    }
}
