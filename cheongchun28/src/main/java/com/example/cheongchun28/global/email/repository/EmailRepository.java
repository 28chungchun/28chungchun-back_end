package com.example.cheongchun28.global.email.repository;

import com.example.cheongchun28.global.email.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    Optional<EmailEntity> findByEmail(String email);

    @Modifying
    @Query("DELETE FROM EmailEntity e WHERE e.modifiedAt <= :limitTime")
    void deleteOlderThanlimitTime(@Param("limitTime")LocalDateTime limitTime);
}
