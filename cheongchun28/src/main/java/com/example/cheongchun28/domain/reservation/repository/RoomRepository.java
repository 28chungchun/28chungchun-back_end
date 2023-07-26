package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByClassName(String className);
}
