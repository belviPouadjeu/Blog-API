package com.belvinard.blog_api.service.impl;

import com.belvinard.blog_api.dtos.CommentDTO;
import com.belvinard.blog_api.entity.Article;
import com.belvinard.blog_api.entity.Comment;
import com.belvinard.blog_api.exceptions.ResourceNotFoundException;
import com.belvinard.blog_api.repositories.ArticleRepository;
import com.belvinard.blog_api.repositories.CommentRepository;
import com.belvinard.blog_api.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public CommentDTO addComment(Long articleId, CommentDTO commentDTO) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "article_id", articleId));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setArticle(article);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDTO.class);
    }

}
