package com.example.cheongchun28.domain.user.service;

import com.example.cheongchun28.domain.user.dto.UserDto;
import com.example.cheongchun28.domain.user.entity.User;
import com.example.cheongchun28.domain.user.repository.UserRepository;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import com.example.cheongchun28.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

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

//        if (requestDto.getEmpNumber().equals("")) {
//
//        }

        userRepository.save(requestDto.toEntity(bcryptPasswordEncoder.encode(requestDto.getPassword())));
        return new CustomResponseDto(200);
    }

    public UserDto.loginResponseDto login(UserDto.loginRequestDto requestDto, HttpServletResponse httpServletResponse) throws SQLException {
        User user = userRepository.findByUserEmail(requestDto.getEmail()).orElseThrow(
                () -> new SQLException()
        );

        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);

        String token = jwtUtil.createToken(afterAuthentication);
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);


        return new UserDto.loginResponseDto(user.getNickName(), user.getProfileImage(), user.getRole());
    }

    public CustomResponseDto checkId(String nickName) {
        User user = userRepository.findByNickName(nickName);
        if (user == null) {
            return new CustomResponseDto(200);
        } else {
            return new CustomResponseDto(204);
        }
    }
}