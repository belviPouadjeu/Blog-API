package com.belvinard.blog_api.service;

import com.belvinard.blog_api.entity.Article;

import java.util.List;

public interface ArticleService {
    Article createArticle(Article article);
    Article getArticleById(Long articleId);
    List<Article> getAllArticles();
    Article updateArticle(Long articleId, Article articleDetails);
    Article deleteArticle(Long articleId);
}