package com.belvinard.blog_api.service;

import com.belvinard.blog_api.dtos.ArticleDTO;
import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.responses.ArticleResponse;

import java.util.List;

public interface ArticleService {
    ArticleResponse getAllArticles();
    ArticleDTO createArticle(ArticleDTO articleDTO);
    ArticleDTO getArticleById(Long articleId);
    ArticleDTO patchArticle(Long articleId, ArticleDTO articleDTO);
    ArticleDTO deleteArticle(Long articleId);

}