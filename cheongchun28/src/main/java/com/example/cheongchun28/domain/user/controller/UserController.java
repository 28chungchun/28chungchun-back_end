package com.example.cheongchun28.domain.user.controller;

import com.example.cheongchun28.domain.user.dto.UserDto;
import com.example.cheongchun28.domain.user.repository.UserRepository;
import com.example.cheongchun28.domain.user.service.UserService;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import com.example.cheongchun28.global.jwt.JwtUtil;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;

    //회원가입
    @PostMapping("/signup")
    public CustomResponseDto signup(@RequestBody UserDto.SignupRequestDto requestDto) {
        log.info("회원가입 시도됨");
        return userService.signup(requestDto);
    }

    //로그인
    @PostMapping("/login")
    public CustomResponseDto login(@RequestBody UserDto.loginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        log.info("로그인 시도됨");
        return userService.login(requestDto, httpServletResponse);
    }
}