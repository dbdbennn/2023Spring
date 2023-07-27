package com.example.study_jpa.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude="article")
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer cid;

    @Column(nullable=false)
    @NonNull
    @NotBlank
    private String writer;

    @Column(nullable=false)
    @NonNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    public void setArticle(Article article) {
        this.article = article;
        // 연관관계 편의 메소드 로직 구현
        List<Comment> comments = article.getComments();
        if(!comments.contains(this)) comments.add(this);
    }
}