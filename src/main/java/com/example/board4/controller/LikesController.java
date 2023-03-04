package com.example.board4.controller;

import com.example.board4.dto.MsgResponseDto;
import com.example.board4.security.UserDetailsImpl;
import com.example.board4.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likesService;

    @PutMapping("/board/{id}")
    public ResponseEntity LikesBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likesService.likesBoard(id, userDetails.getUser());
    }

}
