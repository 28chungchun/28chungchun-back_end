package com.example.cheongchun28.global.email.dto;

import com.example.cheongchun28.global.email.entity.EmailEntity;
import lombok.Getter;

public class EmailDto {

    @Getter
    public static class EmailConfirmRequestDto {
        private String email;
        private String confirmCode;

        public EmailEntity toEntity(EmailDto.EmailConfirmRequestDto requestDto) {
            return new EmailEntity(this.email = requestDto.getEmail(), this.confirmCode = requestDto.getConfirmCode());
        }
    }

    public static class CreateConfimCodeRequestDto {
        private String email;
    }
}
