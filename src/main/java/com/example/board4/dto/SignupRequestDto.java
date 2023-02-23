package com.example.board4.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Size(min = 4, max=12)
    @Pattern(regexp = "[a-z\\d]*${3,12}", message = "글자 수가 맞지 않습니다.")
    private String username;


    @Size(min = 4, max=32)
    @Pattern(regexp = "[a-z\\d]*${3,32}", message = "password가 맞지 않습니다.")
    private String password;
    private boolean admin = false;
    private String adminToken ="";
}
