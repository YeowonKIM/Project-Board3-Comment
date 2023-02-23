package com.example.board4.repository;

import com.example.board4.entity.Board;
import com.example.board4.entity.Comment;
import com.example.board4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
//    Optional<Comment> findByIdAndUser(Long id, User user);
    List<Comment> findAllByBoardOrderByCreateAtAsc(Board board);
}
