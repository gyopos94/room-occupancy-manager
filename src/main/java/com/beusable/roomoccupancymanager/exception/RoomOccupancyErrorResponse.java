package com.beusable.roomoccupancymanager.exception;

import org.springframework.http.HttpStatus;

public class RoomOccupancyErrorResponse {
    private final HttpStatus status;
    private final String message;

    public RoomOccupancyErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}