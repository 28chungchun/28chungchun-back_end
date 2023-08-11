package com.example.cheongchun28.domain.mypage.service;

import com.example.cheongchun28.domain.mypage.dto.MyPageDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.repository.ReservationRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
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
        user.setEmpNumber(requestDto.getEmpNumber());

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

        String newEncodedPassword = bCryptPasswordEncoder.encode(requestDto.getNewPassword());

        if (bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getEncodedPassword())) {
            user.setEncodedPassword(newEncodedPassword);
            userRepository.save(user);
        } else {
            return new CustomResponseDto(400);
        }
        return new CustomResponseDto(200);
    }

    public List<MyPageDto.getMyReservationResponseDto> getMyReservation(User auth) {
        List<Reservation> reservations = reservationRepository.findAllByUser(auth);
        List<MyPageDto.getMyReservationResponseDto> responseDtos = new ArrayList<>();

        if (reservations != null) {
            for (Reservation reservation : reservations) {
                MyPageDto.getMyReservationResponseDto responseDto = new MyPageDto.getMyReservationResponseDto();
                responseDto.setRoomName(reservation.getRoom().getRoomName());
                responseDto.setNickName(reservation.getUser().getNickName());
                responseDto.setStartDate(reservation.getStartDate());
                responseDto.setEndDate(reservation.getEndDate());
                responseDto.setStatus(reservation.getStatus());
                responseDto.setReservationCode(reservation.getCode());

                responseDtos.add(responseDto);
            }
        } else {
            return null;
        }

        return responseDtos;
    }
}
