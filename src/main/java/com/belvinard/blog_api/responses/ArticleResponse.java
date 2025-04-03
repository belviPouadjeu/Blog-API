package com.belvinard.blog_api.responses;

import com.belvinard.blog_api.dtos.ArticleDTO;
import java.util.List;

public class ArticleResponse {
    private List<ArticleDTO> content;

    // Default constructor (needed for instantiation without parameters)
    public ArticleResponse() {}

    // Constructor to initialize with a list
    public ArticleResponse(List<ArticleDTO> content) {
        this.content = content;
    }

    public List<ArticleDTO> getContent() {
        return content;
    }

    public void setContent(List<ArticleDTO> content) {
        this.content = content;
    }
}
