package com.example.study_jpa;

import com.example.study_jpa.entity.Article;
import com.example.study_jpa.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleDTO {
    private Integer id;
    private String title;
    private String writer;
    private String content;
    private List<CommentDTO> comments = new ArrayList<>();

    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.writer = article.getWriter();
        this.content = article.getContent();
    }

    public ArticleDTO(Article article, boolean withComment) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.writer = article.getWriter();
        this.content = article.getContent();
        if(withComment) {
            for(Comment comment : article.getComments()) {
                this.comments.add(new CommentDTO(comment));
            }
        }
    }

    static public Article toEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setWriter(dto.getWriter());
        return article;
    }
}