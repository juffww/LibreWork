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
    UNAUTHENTICATED("Unauthenticated", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    SETTING_NOT_FOUND("Setting", "User setting not found", HttpStatus.NOT_FOUND),
    COMPANY_ALREADY_EXISTS("Company", "Company name already exists", HttpStatus.BAD_REQUEST),
    MISSING_CLIENT_PROFILE_ID("ClientProfile", "Missing client profile ID", HttpStatus.BAD_REQUEST);
    private String field;
    private String message;
    HttpStatus httpStatus;
}
