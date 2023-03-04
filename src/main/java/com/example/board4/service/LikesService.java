package com.example.board4.service;

import com.example.board4.dto.BoardResponseDto;
import com.example.board4.dto.MsgResponseDto;
import com.example.board4.entity.Board;
import com.example.board4.entity.Likes;
import com.example.board4.entity.User;
import com.example.board4.repository.BoardRepository;
import com.example.board4.repository.CommentRepository;
import com.example.board4.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    // 게시글 좋아요
    public ResponseEntity likesBoard(Long id, User user) {
        // 선택한 게시글이 DB에 있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            return exceptionRes("해당 게시글이 없습니다.");
        }

        //
        Optional<Likes> findLikes = likesRepository.findByBoardAndUser(board.get(), user);
        if (findLikes.isEmpty()) {  // 좋아요
            Likes likes = Likes.builder()
                    .board(board.get())
                    .user(user)
                    .build();
            likesRepository.save(likes);
            return ResponseEntity.ok().body(new BoardResponseDto(board.get()));
        } else {  // 좋아요 취소
            likesRepository.delete(findLikes.get());
        }
        return ResponseEntity.ok().body(new BoardResponseDto(board.get()));
    }


    // 예외처리
    private ResponseEntity<MsgResponseDto> exceptionRes(String msg) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(msg, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(msgResponseDto);
    }
}
