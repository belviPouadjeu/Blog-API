package com.belvinard.blog_api.service;

import com.belvinard.blog_api.dtos.CommentDTO;

public interface CommentService {
    CommentDTO addComment(Long articleId, CommentDTO commentDTO);
}
