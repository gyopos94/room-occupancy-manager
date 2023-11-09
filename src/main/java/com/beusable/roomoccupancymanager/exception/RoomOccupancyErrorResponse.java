package com.beusable.roomoccupancymanager.exception;

public class RoomOccupancyErrorResponse {
    private final String message;

    public RoomOccupancyErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}