package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    //예약 생성시 사용
    Optional<Room> findByRoomName(String roomName); // 강의실명으로 찾는다.

    //예약 조회시 사용
    //Optional<Object> findByUser(long userSequenceId); // 예약자 ID로 찾기
}
