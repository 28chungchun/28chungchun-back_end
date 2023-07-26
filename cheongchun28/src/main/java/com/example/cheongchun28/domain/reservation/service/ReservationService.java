package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.Room;
import com.example.cheongchun28.domain.reservation.repository.ReservationRepository;
import com.example.cheongchun28.domain.reservation.repository.RoomRepository;
import com.example.cheongchun28.domain.user.entity.User;
import com.example.cheongchun28.domain.user.repository.UserRepository;
import com.example.cheongchun28.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ReservationService { // 예약 서비스 로직

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final JwtUtil jwtUtil;

    // 예약 생성 서비스
    public ReservationResponseDto.ReservationCreateResponseDto createReservation(ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto, HttpServletRequest httpServletRequest) throws SQLException {
        Reservation reservation = new Reservation();

        String token = jwtUtil.resolveToken(httpServletRequest);  //헤더에서 토큰값 가져오기
        String userEmail = jwtUtil.getUserPk(token); // 이메일 추출

        User user = userRepository.findByUserEmail(userEmail).orElseThrow(
                ()->new SQLException("찾으시는 값이 없습니다.")
        );

        String roomName = reservationCreateRequestDto.getRoomName(); // 강의실명 가져오기
        Room room = roomRepository.findByRoomName(roomName).orElseThrow(
                ()->new SQLException("찾으시는 값이 없습니다.")
        );

        reservation.toEntity(reservationCreateRequestDto.getTopic(), reservationCreateRequestDto.getStartDate(),
                            reservationCreateRequestDto.getEndDate(), room, user, "예약중", createReservationCoder());

        // 예약 정보 저장
        reservationRepository.save(reservation);

        // 생성자를 이용하여 ReservationResponseDto 객체 생성
        return new ReservationResponseDto.ReservationCreateResponseDto(200); // 성공했을때
    }

    //예약 코드(번호) 선언
    private String createReservationCoder() {
        RandomString rs = new RandomString();
        String reservationCode = rs.make(8);

        return reservationCode;
    }

    // 예약 조회 서비스 (전체)
    public ReservationResponseDto.ReservationGetResponseDto getReservation(HttpServletRequest httpServletRequest) {

        String token = jwtUtil.resolveToken(httpServletRequest);  //헤더에서 토큰값 가져오기
        String userEmail = jwtUtil.getUserPk(token); // 이메일 추출

        Reservation reservation = reservationRepository.findWaitingReservationByEmail(userEmail);

        //아이디를 가지고 예약 번호를 찾는다.
        return new ReservationResponseDto.ReservationGetResponseDto(200, reservation.getReservationCode());
    }

    // 예약 조회 서비스 (하나씩)
    public ReservationResponseDto.ReservationGetOneResponseDto getReservation(String reservationCode) {

        Reservation reservation = reservationRepository.findByReservationCode(reservationCode);

        // 예약번호에 해당하는 값들을 찾는다.
        return new ReservationResponseDto.ReservationGetOneResponseDto(reservation.getRoom().getRoomName(),
                reservation.getTopic(), reservation.getUser().getUsername(), reservation.getStatus(), reservation.getStartDate(), reservation.getEndDate());
    }


    // 예약 수정 서비스
    public ReservationResponseDto.ReservationUpdateResponseDto updateReservation(String reservationCode, ReservationRequestDto.ReservationUpdateRequestDto reservationUpdateRequestDto) {

        Reservation reservation = reservationRepository.findByReservationCode(reservationCode); // 예약 코드로 예약 조회

        if (reservation != null) {
            // 날짜 업데이트를 위해 requestDto의 값을 reservation 엔티티에 복사
            reservation.setTopic(reservationUpdateRequestDto.getTopic());
            reservation.setStartDate(reservationUpdateRequestDto.getStartDate());
            reservation.setEndDate(reservationUpdateRequestDto.getEndDate());

            // 수정된 예약 저장
            reservationRepository.save(reservation);

            return new ReservationResponseDto.ReservationUpdateResponseDto(200); // 성공했을때

        } else {
            // 예약이 존재하지 않는 경우 에러 처리 또는 예외 처리 등을 수행...
            return new ReservationResponseDto.ReservationUpdateResponseDto(false, "예약을 찾을 수 없습니다.");
        }


    }

    // 예약 삭제 서비스
    public ReservationResponseDto.ReservationDeleteResponseDto deleteReservation(String email, ReservationRequestDto.ReservationDeleteRequestDto reservationDeleteRequestDto) {

        Reservation reservation = reservationRepository.findWaitingReservationByEmail(email);
        // 이메일 가지고 예약아이디 찾기

        if (reservation != null) {
            // 삭제하기(Delete)  - 예약 상태를 "예약중" -> "예약 취소" 로 변경하는 부분
            // 음 예약 취소하기를 누르면 상태가 변하게끔?
            reservation.setStatus(reservationDeleteRequestDto.getStatus()); // 상태 변경

            // 수정된 예약 저장
            reservationRepository.save(reservation);

            return new ReservationResponseDto.ReservationDeleteResponseDto("예약 취소", 200); // 성공했을때

        } else {
            // 예약이 존재하지 않는 경우 에러 처리 또는 예외 처리 등을 수행...
            return new ReservationResponseDto.ReservationDeleteResponseDto(false, "예약을 찾을 수 없습니다.");
        }




        /*
        Reservation reservation = reservationRepository.findWaitingReservationByEmail(email); // 이메일 가지고 예약아이디 찾기

        //
        if (reservation != null) {
            // 예약 삭제
            reservationRepository.delete(reservation);

            // 삭제 결과 정보를 응답에 담아 반환
            // return new ReservationResponseDto.ReservationDeleteResponseDto(true, "Reservation deleted successfully.");

            return new ReservationResponseDto.ReservationDeleteResponseDto(200); // 성공했을때
        } else {
            // 예약이 존재하지 않는 경우 에러 처리 또는 예외 처리 등을 수행
            // 예를 들면, 예약이 존재하지 않는다는 메시지를 응답으로 보내거나 예외를 던질 수 있습니다.
            // 이 예제에서는 삭제 실패로 처리하도록 가정합니다.
            return new ReservationResponseDto.ReservationDeleteResponseDto(false, "Reservation not found.");
        }
*/
    }
}