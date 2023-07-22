package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationService { // 예약 서비스 로직

    private final ReservationRepository reservationRepository;

    // 예약 생성 서비스
    public ReservationResponseDto.ReservationCreateResponseDto createReservation(ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto) {
        Reservation reservation = new Reservation();

        // 필요한 속성
       // reservation.setUserSequenceId(reservationCreateRequestDto.getUserSequenceId());
        reservation.setTopic(reservationCreateRequestDto.getTopic());
        reservation.setStartDate(reservationCreateRequestDto.getStartDate());
        reservation.setEndDate(reservationCreateRequestDto.getEndDate());

        // 예약 정보 저장
        reservationRepository.save(reservation);

        // 생성자를 이용하여 ReservationResponseDto 객체 생성
        return new ReservationResponseDto.ReservationCreateResponseDto();
    }


    // 예약 조회 서비스
    public ReservationResponseDto.ReservationGetResponseDto getReservation(ReservationRequestDto.ReservationGetRequestDto reservationGetRequestDto) {

        Reservation reservation = new Reservation();

        // 회원 아이디(UserSequenceId)로 조회해서 예약 아이디(reservationId)를 얻어올거임.
        reservation.setUserSequenceId(reservationGetRequestDto.getUserSequenceId());
        reservationRepository.findByUserSequenceId(reservationGetRequestDto);

        return new ReservationResponseDto.ReservationGetResponseDto();
    }

    // 예약 수정 서비스
    public ReservationResponseDto.ReservationUpdateResponseDto updateReservation(Long userSequenceId, ReservationRequestDto.ReservationUpdateRequestDto reservationUpdateRequestDto) {

        // 예약 조회
        //Reservation reservation = reservationRepository.findByUserSequenceId(reservationUpdateRequestDto.updateUserSequenceId());

       Reservation reservation = new Reservation();
       //reservationRepository.findByUserSequenceId(reservationUpdateRequestDto.updateUserSequenceId()); // 예약 조회

       reservationRepository.findById(userSequenceId);// 예약 조회


        if (reservation != null) {
            // 날짜 업데이트를 위해 requestDto의 값을 reservation 엔티티에 복사
            reservation.setStartDate(reservationUpdateRequestDto.getStartDate());
            reservation.setEndDate(reservationUpdateRequestDto.getEndDate());

            // 수정된 예약 저장
            reservation = reservationRepository.save(reservation);

            // 수정된 예약 정보를 응답에 담아 반환
            return new ReservationResponseDto.ReservationUpdateResponseDto(reservation);

        } else {
            // 예약이 존재하지 않는 경우 에러 처리 또는 예외 처리 등을 수행...
            return new ReservationResponseDto.ReservationUpdateResponseDto(false, "예약을 찾을 수 없습니다.");
        }


    }

    // 예약 삭제 서비스
    public ReservationResponseDto.ReservationDeleteResponseDto deleteReservation(Long userSequenceId, ReservationRequestDto.ReservationDeleteRequestDto reservationDeleteRequestDto) {

        // 예약 조회
        Reservation reservation = new Reservation();
        reservationRepository.findById(userSequenceId);

        if (reservation != null) {
            // 예약 삭제
            reservationRepository.delete(reservation);

            // 삭제 결과 정보를 응답에 담아 반환
            return new ReservationResponseDto.ReservationDeleteResponseDto(true, "Reservation deleted successfully.");
        } else {
            // 예약이 존재하지 않는 경우 에러 처리 또는 예외 처리 등을 수행
            // 예를 들면, 예약이 존재하지 않는다는 메시지를 응답으로 보내거나 예외를 던질 수 있습니다.
            // 이 예제에서는 삭제 실패로 처리하도록 가정합니다.
            return new ReservationResponseDto.ReservationDeleteResponseDto(false, "Reservation not found.");
        }


        /*
        Reservation reservation = new Reservation();

        reservation.setUserSequenceId(reservationDeleteRequestDto.deleteUserSequenceId());
        reservationRepository.deleteByUserSequenceId(reservationDeleteRequestDto);

        return new ReservationResponseDto.ReservationDeleteResponseDto();

         */
    }
}