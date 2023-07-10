package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.ReservationEntity;
import com.example.cheongchun28.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
//    Optional<UserEntity> findByUserEmail(String email);  대충 이메일 인증하는 라이브러리인가?
}