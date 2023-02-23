package com.example.board4.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MsgResponseDto {
    private String msg;
    private int statusCode;

    @Builder
    public MsgResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
