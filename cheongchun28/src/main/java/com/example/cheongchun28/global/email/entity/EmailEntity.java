package com.example.cheongchun28.global.email.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "T_EMAIL")
@AllArgsConstructor
@NoArgsConstructor //<- all이 있는데 왜있어야 하는지 의문
@EntityListeners(AuditingEntityListener.class)
public class EmailEntity {

    @Id
    @Column(name = "EMAIl")
    private String email;

    @Column(name = "CONFIRM_CODE")
    private String confirmCode;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public EmailEntity(String email, String confirmCode) {
        this.email = email;
        this.confirmCode = confirmCode;
    }
}


