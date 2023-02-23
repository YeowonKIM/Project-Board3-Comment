package com.example.board4.service;

import com.example.board4.dto.*;
import com.example.board4.dto.MsgResponseDto;
import com.example.board4.entity.Board;
import com.example.board4.entity.Comment;
import com.example.board4.entity.User;
import com.example.board4.entity.UserRoleEnum;
import com.example.board4.repository.BoardRepository;
import com.example.board4.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓글 생성
    @Transactional
    public ResponseEntity createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        // id로 해당 게시글이 DB에 있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            return exceptionRes("해당 게시글이 존재하지 않습니다.");
        } else {
            Comment comment = new Comment(commentRequestDto, user, board.get());
            commentRepository.save(comment);
            return ResponseEntity.ok().body(new CommentResponseDto(comment));

        }

    }

    // 댓글 수정
    @Transactional
    public ResponseEntity updateComment(Long id, CommentRequestDto commentRequestDto, User user){

        // id로 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            return exceptionRes("해당 댓글이 존재하지 않습니다.");
        }

        // 댓글 작성자와 토큰에서의 정보가 일치하는지 확인, 일피하면 댓글 삭제
        // Admin인 경우 수정 권한
        Optional<Board> compare = boardRepository.findByIdAndUser(id, user);
        if (compare.isEmpty() && user.getRole()== UserRoleEnum.USER) {
            return exceptionRes("작성자 정보가 없습니다.");
        } else if (user.getRole() == UserRoleEnum.ADMIN) {
            comment = commentRepository.findById(id);
        }

        // 이외의 경우는 게시글 수정 가능
        comment.get().update(commentRequestDto, user);
        return ResponseEntity.ok().body(new CommentResponseDto(comment.get()));
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity deleteComment(Long id, User user) {

        // id로 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            return exceptionRes("해당 댓글이 존재하지 않습니다.");
        }

        // 댓글 작성자와 토큰에서의 정보가 일치하는지 확인, 일피하면 댓글 삭제
        // Admin인 경우 삭제 권한
        Optional<Board> compare = boardRepository.findByIdAndUser(id, user);
        if (compare.isEmpty() && user.getRole()== UserRoleEnum.USER) {
            return exceptionRes("작성자 정보가 없습니다.");
        } else if (user.getRole() == UserRoleEnum.ADMIN) {
            comment = commentRepository.findById(id);
        }

        // 이외의 경우는 게시글 삭제 가능
        commentRepository.deleteById(id);
        return ResponseEntity.ok().body(new CommentResponseDto(comment.get()));
    }

    // 예외처리
    private ResponseEntity<MsgResponseDto> exceptionRes(String msg) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(msg, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(msgResponseDto);
    }
}

