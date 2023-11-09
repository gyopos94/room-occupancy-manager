package com.beusable.roomoccupancymanager.exception;

import org.springframework.http.HttpStatus;

public class RoomOccupancyErrorResponse {
    private final String message;

    public RoomOccupancyErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}