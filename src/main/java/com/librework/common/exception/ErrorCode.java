package com.librework.common.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_EXISTED("Email", "Email is exited", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User", "User is not existed", HttpStatus.BAD_REQUEST),
    ASSET_NOT_EXITSTED("Asset", "Asset is not existed", HttpStatus.BAD_REQUEST),
    WATCHLIST_NOT_EXISTED("Watchlist", "Watchlist is not existed", HttpStatus.BAD_REQUEST),
    ASSET_EXISTED_IN_WATCHLIST("Asset/Watchlist", "Asset is already in watchlist", HttpStatus.BAD_REQUEST);

    private String field;
    private String message;
    HttpStatus httpStatus;
}
