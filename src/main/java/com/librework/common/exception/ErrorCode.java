package com.librework.common.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_EXISTED("Email", "Email is exited", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User", "User is not existed", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED("Username", "Username is exited", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FALSE("Email Or Password", "Email Or Password is wrong", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("Unauthenticated", "Unauthenticated", HttpStatus.UNAUTHORIZED);

    private String field;
    private String message;
    HttpStatus httpStatus;
}
