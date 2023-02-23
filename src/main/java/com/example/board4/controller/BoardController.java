package com.example.board4.controller;

import com.example.board4.dto.BoardDeleteDto;
import com.example.board4.dto.BoardRequestDto;
import com.example.board4.dto.BoardResponseDto;
import com.example.board4.security.UserDetailsImpl;
import com.example.board4.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/api/post")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto, userDetails.getUser());
    }

    // 게시글 조회
    @GetMapping("/api/posts")
    public ResponseEntity getBoards() {
        return boardService.getBoards();
    }

    // 게시글 선택 조회
    @GetMapping("/api/post/{id}")
    public ResponseEntity getBorad(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    // 게시글 수정
    @PutMapping("/api/post/{id}")
    public ResponseEntity updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, boardRequestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }
}
