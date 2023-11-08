package com.beusable.roomoccupancymanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoomOccupancyExceptionHandler {

    @ExceptionHandler(RoomOccupancyException.class)
    public ResponseEntity<RoomOccupancyErrorResponse> handleRoomOccupancyException(RoomOccupancyException ex) {
        RoomOccupancyErrorResponse errorResponse = new RoomOccupancyErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}