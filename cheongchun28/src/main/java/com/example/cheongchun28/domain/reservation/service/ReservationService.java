package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.ReservationStatus;
import com.example.cheongchun28.domain.reservation.entity.Room;
import com.example.cheongchun28.domain.reservation.repository.ReservationRepository;
import com.example.cheongchun28.domain.reservation.repository.RoomRepository;
import com.example.cheongchun28.domain.user.entity.User;
import com.example.cheongchun28.domain.user.repository.UserRepository;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {


    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    @Transactional
    public CustomResponseDto createReservation(User auth, ReservationRequestDto.CreateReservationDto createReservationDto) {
        try {
            User user = userRepository.findByUserEmail(auth.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
            log.info("user: {}", user);
            Room room = roomRepository.findByClassName(createReservationDto.getClassName())
                    .orElseThrow(() -> new IllegalArgumentException(createReservationDto.getClassName() + "를 찾을 수 없습니다."));

            // 방 예약 존재 체크
            if (isRoomAlreadyReserved(room, createReservationDto.getStartTime(), createReservationDto.getEndTime())) {
                log.error("동일한 방 예약 중복");
                return new CustomResponseDto(400);
            }
            // 다른 예약 체크
            if (doesUserTimeOverlap(user, createReservationDto.getStartTime(), createReservationDto.getEndTime())) {
                log.error("사용자 예약 중복");
                return new CustomResponseDto(400);
            }
            // 예약 체크
            if (hasInProgressReservation(user)) {
                log.error("이미 예약을 잡은 회원입니다.");
                return new CustomResponseDto(400);
            }
            // 예약 시간 중복 체크
            List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(createReservationDto.getClassName(), createReservationDto.getStartTime(), createReservationDto.getEndTime());
            if (!overlappingReservations.isEmpty()) {
                log.error("예약 시간 중복입니다.");
                return new CustomResponseDto(400);
            }
            // 예약 시간간격 체크
            if (createReservationDto.isMinimumHourInterval(createReservationDto.getStartTime(), createReservationDto.getEndTime())) {
                reservationRepository.save(createReservationDto.toEntity(room, user));
                return new CustomResponseDto(200);
            } else {
                log.error("예약 시간 간격이 1시간 이상 차이나지 않습니다.");
                return new CustomResponseDto(400);
            }
        } catch (UsernameNotFoundException | IllegalArgumentException e) {
            log.error("예약 생성 중 오류가 발생했습니다: {}", e.getMessage());
            return new CustomResponseDto(400);
        } catch (Exception e) {
            log.error("예약 생성 중 오류가 발생했습니다: {}", e.getMessage());
            return new CustomResponseDto(500);
        }
    }


    private boolean isRoomAlreadyReserved(Room room, LocalDateTime startTime, LocalDateTime endTime) {
        return room.isRoomReserved(startTime, endTime);
    }

    private boolean doesUserTimeOverlap(User user, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> existingReservations = reservationRepository.findByUserAndStartTimeBetweenAndEndTimeBetween(user, startTime, endTime, startTime, endTime);
        return !existingReservations.isEmpty();
    }

    private boolean hasInProgressReservation(User user) {
        return user.getReservations().stream()
                .anyMatch(reservation -> reservation.getStatus() != ReservationStatus.CANCELLED && reservation.getStatus() != ReservationStatus.COMPLETED);
    }


}
