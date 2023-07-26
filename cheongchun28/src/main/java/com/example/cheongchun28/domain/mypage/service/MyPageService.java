package com.example.cheongchun28.domain.mypage.service;

import com.example.cheongchun28.domain.mypage.dto.MyPageDto;
import com.example.cheongchun28.domain.user.entity.User;
import com.example.cheongchun28.domain.user.repository.UserRepository;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import com.example.cheongchun28.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomResponseDto changeMyInfo(MyPageDto.ChangeMyInfoRequestDto requestDto, HttpServletRequest httpServletRequest) throws SQLException {
        String email = jwtUtil.getEmailAtToken(jwtUtil.getToken(httpServletRequest));
        User user = userRepository.findByUserEmail(email).orElseThrow(
                () -> new SQLException("찾으시는 데이터가 없습니다.")
        );

        user.setNickName(requestDto.getNickName());
        user.setProfileImage(requestDto.getProfileImage());
        user.setNotificationAgreement(requestDto.isNotificationAgreement());

        userRepository.save(user);

        return new CustomResponseDto(200);
    }

    public MyPageDto.getMyInfoResponseDto getMyInfo(HttpServletRequest httpServletRequest) throws SQLException {
        String email = jwtUtil.getEmailAtToken(jwtUtil.getToken(httpServletRequest));
        User user = userRepository.findByUserEmail(email).orElseThrow(
                () -> new SQLException("찾으시는 데이터가 없습니다.")
        );

        log.info(user.getNickName());
        return new MyPageDto.getMyInfoResponseDto(user.getUserEmail(), user.getNickName(), user.getProfileImage(), user.getEmpNumber());
    }

    public CustomResponseDto changeMyPassword(MyPageDto.ChangeMyPasswordRequestDto requestDto, HttpServletRequest httpServletRequest) throws SQLException {
        String email = jwtUtil.getEmailAtToken(jwtUtil.getToken(httpServletRequest));
        User user = userRepository.findByUserEmail(email).orElseThrow(
                () -> new SQLException("찾으시는 데이터가 없습니다.")
        );
        String encodedPassword = user.getEncodedPassword();
        String newEncodedPassword = bCryptPasswordEncoder.encode(requestDto.getNewPassword());

        if (user.getPassword().equals(encodedPassword)) {
            user.setEncodedPassword(newEncodedPassword);
            userRepository.save(user);
        } else {
            return new CustomResponseDto(400);
        }
        return new CustomResponseDto(200);
    }

    public MyPageDto.getMyReservationResponseDto getMyReservation(HttpServletRequest httpServletRequest) {
        return null;
    }
}
