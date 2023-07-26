package com.example.cheongchun28.global.email.controller;

import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import com.example.cheongchun28.global.email.dto.EmailDto;
import com.example.cheongchun28.global.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {
    private final EmailService emailService;

    //이메일 인증 코드 발급 및 전송
    @PostMapping("/email/send")
    public CustomResponseDto sendEmailConfirmMail(@RequestParam String recipient) throws Exception {
        emailService.sendEmailConfirmMail(recipient);
        return new CustomResponseDto(200);
    }

    //이메일 인증 코드 비교
    @PostMapping("/email/confirm")
    public CustomResponseDto emailConfirm(@RequestBody EmailDto.EmailConfirmRequestDto emailDto) throws Exception {
        return emailService.emailConfirm(emailDto);
    }

    @PostMapping("/email/reset-password/{recipient}")
    public CustomResponseDto sendResetPasswordMail(@PathVariable String recipient) throws Exception {
        emailService.sendEmailResetPasswordMail(recipient);
        return new CustomResponseDto(200);
    }
}
