package com.belvinard.blog_api.config;

import com.belvinard.blog_api.dtos.ArticleDTO;
import com.belvinard.blog_api.dtos.CommentDTO;
import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // Map Article -> ArticleDTO
        modelMapper.typeMap(Article.class, ArticleDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Article::getPublicationDate, ArticleDTO::setPublicationDate);
                    mapper.map(Article::getLastUpdated, ArticleDTO::setLastUpdated);
                    mapper.map(Article::getComments, ArticleDTO::setComments);
                });

        // ✅ Map Comment -> CommentDTO including createdAt
        modelMapper.typeMap(Comment.class, CommentDTO.class)
                .addMappings(mapper
                        -> mapper.map(Comment::getCreatedAt, CommentDTO::setCreatedAt));

        return modelMapper;
    }




}