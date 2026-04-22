package com.librework.modules.identity.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private UUID id;

    private String email;

    private String password;

    private String fullName;

    private String username;

    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void suspend() {
        if (this.status == UserStatus.DEACTIVATED) {
            throw new IllegalStateException("Không thể khóa tài khoản đã bị vô hiệu hóa vĩnh viễn.");
        }
        this.status = UserStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.status == UserStatus.ACTIVE) {
            return;
        }
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = UserStatus.DEACTIVATED;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePassword(String newHashedPassword) {
        if (newHashedPassword == null || newHashedPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        this.password = newHashedPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public enum UserStatus {
        ACTIVE, SUSPENDED, DEACTIVATED
    }
}
