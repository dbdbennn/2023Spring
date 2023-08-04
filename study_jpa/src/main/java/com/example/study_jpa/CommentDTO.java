package com.example.study_jpa;

import com.example.study_jpa.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Integer cid;
    private String writer;
    private String content;

    public CommentDTO(Comment comment) {
        this.cid = comment.getCid();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
    }

    static public Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setCid(dto.getCid());
        comment.setContent(dto.getContent());
        comment.setWriter(dto.getWriter());
        return comment;
    }
}
