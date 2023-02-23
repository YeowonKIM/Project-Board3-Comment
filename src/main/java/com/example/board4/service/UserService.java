package com.example.board4.service;

import com.example.board4.dto.LoginRequestDto;
import com.example.board4.dto.MsgResponseDto;
import com.example.board4.dto.SignupRequestDto;
import com.example.board4.entity.UserRoleEnum;
import com.example.board4.entity.User;
import com.example.board4.jwt.JwtUtil;
import com.example.board4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    // 회원가입
    @Transactional
    public ResponseEntity<MsgResponseDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 중복 회원가입 여부 확인
        Optional<User> find = userRepository.findByUsername(username);
        if (find.isPresent()) {
//            throw new RestApiException(ErrorCode.DUPLICATED_USERNAME);
            return exceptionRes("중복된 가입입니다.");
        }

        // 권한확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!(signupRequestDto.getAdminToken().equals(ADMIN_TOKEN))) {
                return exceptionRes("관리자 권한이 아닙니다.");
            }
            role = UserRoleEnum.ADMIN;
        }


        User user = User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        userRepository.save(user);

        MsgResponseDto good = MsgResponseDto.builder()
                .msg("회원가입 완료")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(good);
    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Optional<User> user = userRepository.findByUsername(username); // DB에서 회원 얻어오기
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())){
            return exceptionRes("회원 정보가 일치하지 않습니다.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(username, user.get().getRole()));

        MsgResponseDto msgResponseDto = new MsgResponseDto("로그인 성공입니다.",HttpStatus.OK.value());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(msgResponseDto);
    }

    // 예외처리
    private ResponseEntity<MsgResponseDto> exceptionRes(String msg) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(msg, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(msgResponseDto);
    }

}
