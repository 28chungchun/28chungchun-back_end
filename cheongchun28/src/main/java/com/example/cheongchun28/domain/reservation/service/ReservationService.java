package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.entity.*;
import com.example.cheongchun28.domain.reservation.repository.ReservationMemberRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {


    private final ReservationRepository reservationRepository;
    private final ReservationMemberRepository reservationMemberRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    // 예약 생성
    @Transactional
    public CustomResponseDto createReservation(User auth, ReservationRequestDto.CreateReservationDto createReservationDto) {
        try {
            User user = userRepository.findByUserEmail(auth.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
            log.info("user: {}", user);
            Room room = roomRepository.findByClassName(createReservationDto.getClassName())
                    .orElseThrow(() -> new IllegalArgumentException(createReservationDto.getClassName() + "를 찾을 수 없습니다."));
            // 방 예약 및 다른 예약 존재 체크
            if (hasOverlappingReservation(room, user, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
                log.error("예약 중복");
                return new CustomResponseDto(400);
            }
            // 방 예약 존재 체크
            if (isRoomAlreadyReserved(room, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
                log.error("동일한 방 예약 중복");
                return new CustomResponseDto(400);
            }
            // 다른 예약 체크
            if (doesUserTimeOverlap(user, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
                log.error("사용자 예약 중복");
                return new CustomResponseDto(400);
            }
            // 예약 체크
            if (hasInProgressReservation(user)) {
                log.error("이미 예약을 잡은 회원입니다.");
                return new CustomResponseDto(400);
            }
            // 예약 시간 중복 체크
            List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(createReservationDto.getClassName(), createReservationDto.getStartDate(), createReservationDto.getEndDate());
            if (!overlappingReservations.isEmpty()) {
                log.error("예약 시간 중복입니다.");
                return new CustomResponseDto(400);
            }
            // 예약 시간간격 체크
            if (createReservationDto.isValid()) {
                reservationRepository.save(createReservationDto.toEntity(room, user));
                return new CustomResponseDto(200);
            } else {
                log.error("최소 예약 시간은 1시간 이상이어야 합니다.");
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

    public boolean hasOverlappingReservation(Room room, User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> roomReservations = reservationRepository.findByRoomAndStartDateBetweenAndEndDateBetween(room, startDate, endDate, startDate, endDate);
        List<Reservation> userReservations = reservationRepository.findByUserAndStartDateBetweenAndEndDateBetween(user, startDate, endDate, startDate, endDate);

        return !roomReservations.isEmpty() || !userReservations.isEmpty();
    }

    public boolean isRoomAlreadyReserved(Room room, LocalDateTime startDate, LocalDateTime endDate) {
        return room.isRoomReserved(startDate, endDate);
    }

    public boolean doesUserTimeOverlap(User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> existingReservations = reservationRepository.findByUserAndStartDateBetweenAndEndDateBetween(user, startDate, endDate, startDate, endDate);
        return !existingReservations.isEmpty();
    }

    public boolean hasInProgressReservation(User user) {
        return user.getReservations().stream()
                .anyMatch(reservation -> reservation.getStatus() != ReservationStatus.CANCELLED && reservation.getStatus() != ReservationStatus.COMPLETED);
    }


    // 예약 수정
    @Transactional
    public CustomResponseDto updateReservation(User auth, String code, ReservationRequestDto.UpdateReservationDto updateReservationDto) {
        try {
            Reservation reservation = reservationRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException(code + "를 찾을 수 없습니다."));


            if (updateReservationDto.isValid()) {
                reservation.updateReservation(updateReservationDto);
                reservationRepository.save(reservation);
                return new CustomResponseDto(200);
            } else {
                log.error("예약 시간 간격이 1시간 이상 차이나지 않습니다.");
                return new CustomResponseDto(400);
            }
        } catch (IllegalArgumentException e) {
            log.error("예약 수정 중 오류가 발생했습니다: {}", e.getMessage());
            return new CustomResponseDto(400);
        } catch (Exception e) {
            log.error("예약 수정 중 오류가 발생했습니다: {}", e.getMessage());
            return new CustomResponseDto(500);
        }

    }


    // 예약 조회 서비스 (전체)
    public ReservationResponseDto.ReservationGetResponseDto getReservation(User auth) {

        User user = userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findWaitingReservationByEmail(user.getUserEmail());
        //아이디를 가지고 예약 번호를 찾는다.
        return  new ReservationResponseDto.ReservationGetResponseDto(200, reservation.getCode());
    }

    // 예약 조회 서비스 (하나씩)
    public ReservationResponseDto.ReservationGetOneResponseDto getReservation(String code) {

        Reservation reservation = reservationRepository.findByReservationCode(code);
        List<ReservationMember> reservationMember = reservationMemberRepository.findByReservation(ReservationMemberStatus.CONFIRMED, reservation.getId());
//        List<ReservationMember> reservationMember = reservationMemberRepository.findByReservation(reservation);
        ArrayList<String> memberUser = new ArrayList<>();
        for (ReservationMember rm : reservationMember) {
            memberUser.add(rm.getUser().getNickName());
        }
        // 예약번호에 해당하는 값들을 찾는다.
        return new ReservationResponseDto.ReservationGetOneResponseDto(reservation.getRoom().getClassName(),
                reservation.getTopic(), reservation.getUser().getNickName(), reservation.getStatus(),
                reservation.getStartDate(), reservation.getEndDate(), memberUser);
    }

    // 예약 삭제
    @Transactional
    public CustomResponseDto deleteReservation(User auth, String code) {
        userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException(code + "를 찾을 수 없습니다."));
        reservation.deleteReservation();
        reservationRepository.save(reservation);
        return new CustomResponseDto(200);
    }

    // 예약 참가
    @Transactional
    public CustomResponseDto joinReservation(User auth, String code) {
        User user =  userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다: " + code));
        // 유저 예약 생성 체크
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            log.error("기존 예약이 있습니다.");
            return new CustomResponseDto(400);
        }

        ReservationMember existingReservationMember = reservationMemberRepository.findByReservationAndUser(reservation, user);
        if (existingReservationMember != null) {
            log.error("이미 참여중인 회원입니다.");
            return new CustomResponseDto(400);
        }
        reservationMemberRepository.save(
                ReservationMember.builder()
                        .reservation(reservation)
                        .user(auth)
                        .build()
        );
        return new CustomResponseDto(200);
    }

    // 얘약 참가 취소
    public CustomResponseDto joinCancelReservation(User auth, String code) {
        User user =  userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다: " + code));

        ReservationMember reservationMember = reservationMemberRepository.findByReservationAndUser(reservation, user);

        if (reservationMember.getStatus() == ReservationMemberStatus.CANCELLED) {
            log.error("이미 취소된 예약입니다.");
            return new CustomResponseDto(400);
        }

        reservationMember.cancelReservationMember();
        return new CustomResponseDto(200);
    }


}
