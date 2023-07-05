package com.example.cheongchun28.domain.user.service;

import com.example.cheongchun28.domain.user.dto.UserDto;
import com.example.cheongchun28.domain.user.entity.User;
import com.example.cheongchun28.domain.user.repository.UserRepository;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import com.example.cheongchun28.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public CustomResponseDto signup(UserDto.SignupRequestDto requestDto) {
        userRepository.save(requestDto.toEntity(bcryptPasswordEncoder.encode(requestDto.getPassword())));
        return new CustomResponseDto(200);
    }

    public CustomResponseDto login(UserDto.loginRequestDto requestDto, HttpServletResponse httpServletResponse) {

        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);

        String token = jwtUtil.createToken(afterAuthentication);
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return new CustomResponseDto(200);
    }
}