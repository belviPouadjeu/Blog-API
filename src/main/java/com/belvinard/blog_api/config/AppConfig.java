package com.belvinard.blog_api.config;

import com.belvinard.blog_api.dtos.ArticleDTO;
import com.belvinard.blog_api.entity.Article;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // ✅ Ensure LocalDateTime is correctly mapped
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // ✅ Explicit field mapping (optional but recommended)
        modelMapper.typeMap(Article.class, ArticleDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Article::getPublicationDate, ArticleDTO::setPublicationDate);
                    mapper.map(Article::getLastUpdated, ArticleDTO::setLastUpdated);
                });

        return modelMapper;
    }


}