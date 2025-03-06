package com.packup.domain.sample.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dev_user")
public class DevUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "user_id", nullable = false, unique = true, length = 255)
    private String userId;

    @Column(name = "rgst_day", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registeredAt;

    @Column(name = "updt_day", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.registeredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public DevUser(Long id, String userName, String userId, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
    }
}