package com.beusable.roomoccupancymanager.exception;

public class RoomOccupancyErrorResponse {
    private String message;
    private int status;

    public RoomOccupancyErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}