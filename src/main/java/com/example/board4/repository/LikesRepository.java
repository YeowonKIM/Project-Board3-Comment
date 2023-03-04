package com.example.board4.repository;

import com.example.board4.entity.Board;
import com.example.board4.entity.Comment;
import com.example.board4.entity.Likes;
import com.example.board4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository <Likes, Long> {
    Optional<Likes> findByBoardAndUser(Board board, User user);
    Optional<Likes> findByCommentAndUser(Comment comment, User user);
}
