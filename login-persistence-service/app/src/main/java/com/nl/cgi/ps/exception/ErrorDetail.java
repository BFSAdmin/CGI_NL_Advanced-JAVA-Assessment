package com.nl.cgi.ps.exception;

import org.springframework.http.HttpStatus;

public enum ErrorDetail {

    INTERNAL_SERVER_ERROR("PS500", "Internal server error please try later", HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENT_BAD_REQUEST_EXCEPTION("PS500", "Bad Request error from service", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    ErrorDetail(String id, String msg, HttpStatus status) {
        this.errorCode = id;
        this.errorMessage = msg;
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
