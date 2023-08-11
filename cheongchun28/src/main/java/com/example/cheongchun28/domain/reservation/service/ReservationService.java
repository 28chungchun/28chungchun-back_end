package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.ReservationMember;
import com.example.cheongchun28.domain.reservation.entity.ReservationStatus;
import com.example.cheongchun28.domain.reservation.entity.Room;
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

import javax.validation.constraints.Null;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            log.info("1startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());
            Room room = roomRepository.findByRoomName(createReservationDto.getRoomName())
                    .orElseThrow(() -> new IllegalArgumentException(createReservationDto.getRoomName() + "를 찾을 수 없습니다."));
//             방 예약 및 다른 예약 존재 체크
//            if (hasOverlappingReservation(room, user, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
//                log.error("예약 중복");
//                return new CustomResponseDto(400);
//            }
            // 방 예약 존재 체크
            if (isRoomAlreadyReserved(room, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
                log.error("동일한 방 예약 중복");
                return new CustomResponseDto(400);
            }
            log.info("2startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());
//            List<Reservation> completedOrCancelledReservations = reservationRepository.findByUserAndStatusIn(user.getUserSequenceId(), List.of(ReservationStatus.CANCELLED, ReservationStatus.COMPLETED));
//            if (doesOverlapWithReservations(completedOrCancelledReservations, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
//                log.error("예약하려는 회원님의 이전에 완료된 예약이 겹칩니다.");
//                return new CustomResponseDto(400);
//            }
            log.info("3startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());

            // 참여 중인 회원의 중복 예약 확인
//            List<ReservationMember> overlappingReservationsByUser = reservationMemberRepository.findOverlappingReservationsByUser(user.getUserSequenceId(), createReservationDto.getStartDate(), createReservationDto.getEndDate());
//            if (!overlappingReservationsByUser.isEmpty()) {
//                log.error("회원님이 참여 중인 다른 예약이 겹칩니다.");
//                return new CustomResponseDto(400);
//            }
            log.info("4startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());
            // 다른 예약 체크
            if (doesUserTimeOverlap(user, createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
                log.error("사용자 예약 중복");
                return new CustomResponseDto(400);
            }
            log.info("5startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());
            // 예약 체크
            if (hasInProgressReservation(user)) {
                log.error("이미 예약을 잡은 회원입니다.");
                return new CustomResponseDto(400);
            }
            log.info("6startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());
            // 예약 시간 중복 체크
            List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(createReservationDto.getRoomName(), createReservationDto.getStartDate(), createReservationDto.getEndDate());
            if (!overlappingReservations.isEmpty()) {
                log.error("예약 시간 중복입니다.");
                return new CustomResponseDto(400);
            }
            log.info("7startDate:{}, endDate:{}, roomName:{}, topic:{}", createReservationDto.getStartDate(), createReservationDto.getEndDate(), createReservationDto.getRoomName(), createReservationDto.getTopic());
            // 예약 시간간격 체크
            if (isValid(createReservationDto.getStartDate(), createReservationDto.getEndDate())) {
                Reservation reservation = reservationRepository.save(createReservationDto.toEntity(room, user));
                ReservationMember reservationMember = ReservationMember.builder()
                        .reservation(reservation)
                        .user(user)
                        .build();
                reservationMember.setInvitor();
                reservationMemberRepository.save(reservationMember);
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

    private boolean doesOverlapWithReservations(List<Reservation> reservations, LocalDateTime startDate, LocalDateTime endDate) {
        for (Reservation reservation : reservations) {
            if ((startDate.isEqual(reservation.getStartDate()) || startDate.isAfter(reservation.getStartDate())) && startDate.isBefore(reservation.getEndDate()) ||
                    (endDate.isEqual(reservation.getEndDate()) || endDate.isBefore(reservation.getEndDate())) && endDate.isAfter(reservation.getStartDate())) {
                return true;
            }
        }
        return false;
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

    public boolean isValid(LocalDateTime startDate, LocalDateTime endDate) {
        return isMinimumHourInterval(startDate, endDate);
    }

    public boolean isMinimumHourInterval(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        return duration.toHours() >= 1;
    }

    // 예약 조회 서비스 (본인)
    public ReservationResponseDto.ReservationGetResponseDto getReservation(User auth) {

        User user = userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findWaitingReservationByEmail(user.getUserEmail());

        //참가자인 경우
        List<ReservationMember> members = reservationMemberRepository.findReservationMemberByUser(user);
        for (ReservationMember rm : members) {
            if (rm.isStatus() == false) {
                Reservation rsv = reservationRepository.findById(rm.getReservation().getId()).orElseThrow();

                return new ReservationResponseDto.ReservationGetResponseDto(200, rsv.getCode());
            }
        }
        log.info("members: {}", members.size(), "rsv");

        String reservationCode;
        try {
            reservationCode = reservation.getCode();
        } catch (NullPointerException e) {
            return new ReservationResponseDto.ReservationGetResponseDto(204, "");
        }
        return new ReservationResponseDto.ReservationGetResponseDto(200, reservationCode);
    }

    // 예약 조회 서비스 (하나씩)
    public ReservationResponseDto.ReservationGetOneResponseDto getReservation(String code) {
        Reservation reservation = reservationRepository.findByReservationCode(code);
        List<ReservationMember> reservationMember = reservationMemberRepository.findByReservation(false, reservation.getId());

        ArrayList<String> memberUser = new ArrayList<>();
        for (ReservationMember rm : reservationMember) {
            memberUser.add(rm.getUser().getNickName());
        }

        return new ReservationResponseDto.ReservationGetOneResponseDto(reservation.getRoom().getRoomName(),
                reservation.getTopic(), reservation.getUser().getNickName(), reservation.getStatus(),
                reservation.getStartDate(), reservation.getEndDate(), memberUser);

    }

    // 예약 수정
    @Transactional
    public CustomResponseDto updateReservation(User auth, String code, ReservationRequestDto.UpdateReservationDto
            updateReservationDto) {
        try {
            Reservation reservation = reservationRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException(code + "를 찾을 수 없습니다."));

            if (!auth.getUserSequenceId().equals(reservation.getUser().getUserSequenceId())) {
                log.info(String.valueOf(reservation.getId()));
                log.info(String.valueOf(auth.getUserSequenceId()));
                log.info("본인이 예약한 예약이 아님");
                return new CustomResponseDto(400);
            }
            if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
                log.info("삭제된 예약");
                return new CustomResponseDto(400);
            }
            if (isRoomAlreadyReserved(reservation.getRoom(), updateReservationDto.getStartDate(), updateReservationDto.getEndDate())) {
                log.error("동일한 방 예약 중복");
                return new CustomResponseDto(400);
            }
            if (isValid(updateReservationDto.getStartDate(), updateReservationDto.getEndDate())) {
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

    // 예약 삭제
    @Transactional
    public CustomResponseDto deleteReservation(User auth, String code) {
        userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException(code + "를 찾을 수 없습니다."));
        reservation.deleteReservation();
        List<ReservationMember> reservationMember = reservationMemberRepository.findByReservation(false, reservation.getId());
        if (reservationMember != null) {
            for (ReservationMember member : reservationMember) {
                member.cancelReservationMember();
                reservationMemberRepository.save(member);
            }
        }
        reservationRepository.save(reservation);
        return new CustomResponseDto(200);
    }

    // 예약 참가
    @Transactional
    public CustomResponseDto joinReservation(User auth, String code) {
        User user = userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다: " + code));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            log.error("예약가능한 상태가 아닙니다.");
            return new CustomResponseDto(400);
        }

        ReservationMember existingReservationMember = reservationMemberRepository.findByReservationAndUser(reservation, user);
        if (existingReservationMember != null && !existingReservationMember.isStatus()) {
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

    // 예약 참가 취소
    public CustomResponseDto joinCancelReservation(User auth, String code) {
        User user = userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다: " + code));

        ReservationMember reservationMember = reservationMemberRepository.findByReservationAndUser(reservation, user);

        reservationMemberRepository.delete(reservationMember);
        return new CustomResponseDto(200);
    }

    // 강의실 체크인
    @Transactional
    public CustomResponseDto checkInReservation(User auth, String code) {
        User user = userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(auth.getUsername() + "를 찾을 수 없습니다."));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다: " + code));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            log.error("기존 예약이 있습니다.");
            return new CustomResponseDto(400);
        }

        ReservationMember existingReservationMember = reservationMemberRepository.findByReservationAndUser(reservation, user);
        if (existingReservationMember != null && !existingReservationMember.isStatus()) {
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

    // 강의실 체크아웃
    public CustomResponseDto checkOutReservation(User auth, String code) {
        User user = userRepository.findByUserEmail(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(""));
        Reservation reservation = reservationRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException());

        ReservationMember reservationMember = reservationMemberRepository.findByReservationAndUser(reservation, user);

        if (reservationMember.isStatus()) {
            return new CustomResponseDto(400);
        }

        reservationMember.checkOutReservationMember();
        reservationMemberRepository.save(reservationMember);
        return new CustomResponseDto(200);
    }
}