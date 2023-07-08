package com.example.cheongchun28.domain.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Table(name = "T_USER")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_SEQUENCE_ID", nullable = false)
    private Long userSequenceId;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "ROLE_SEQUENCE_ID", nullable = false)
    private RoleEntity roleEntity;

    @Column(name = "EMAIL", nullable = false)
    private String userEmail;

    @Column(name = "PASSWORD", nullable = false)
    private String encodedPassword;

    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @Column(name = "PROFILE_IMAGE", nullable = false)
    private String profileImage;

    @Column(name = "DELETED", nullable = false)
    private short userDeleted;

    @Column(name = "EMP_NUMBER", nullable = true)
    private String empNumber;

    public UserEntity(String userEmail, String password, String userName, String profileImage, String empNumber) {
        this.roleEntity = RoleEntity.USER;
        this.userEmail = userEmail;
        this.encodedPassword = password;
        this.userName = userName;
        this.profileImage = profileImage;
        this.userDeleted = 1;
        this.empNumber = empNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = this.roleEntity.getRole();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }
    @Override
    public String getPassword() {
        return this.encodedPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
