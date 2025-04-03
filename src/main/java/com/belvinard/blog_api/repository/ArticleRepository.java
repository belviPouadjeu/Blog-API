package com.belvinard.blog_api.repository;

import com.belvinard.blog_api.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);
}