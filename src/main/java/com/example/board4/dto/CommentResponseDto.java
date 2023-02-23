package com.example.board4.dto;

import com.example.board4.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifyedAt;
    private String username;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreateAt();
        this.modifyedAt = comment.getModifiedAt();
        this.username = comment.getUser().getUsername();

    }
}
