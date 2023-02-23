package com.example.board4.controller;

import com.example.board4.dto.LoginRequestDto;
import com.example.board4.dto.MsgResponseDto;
import com.example.board4.dto.SignupRequestDto;
import com.example.board4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        //에러가 터지면 BindingResult에 담김.
        if(bindingResult.hasErrors()){
            MsgResponseDto msgResponseDto = new MsgResponseDto(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(msgResponseDto);
        } //에러가 생기면 hasErrors에 개수로 담김
        return userService.signup(signupRequestDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
