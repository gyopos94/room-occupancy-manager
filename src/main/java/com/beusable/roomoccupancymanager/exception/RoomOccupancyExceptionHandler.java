package com.beusable.roomoccupancymanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoomOccupancyExceptionHandler {

    @ExceptionHandler(RoomOccupancyException.class)
    public ResponseEntity<RoomOccupancyErrorResponse> handleRoomOccupancyException(RoomOccupancyException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        RoomOccupancyErrorResponse errorResponse = new RoomOccupancyErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<RoomOccupancyErrorResponse> handleGenericException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        RoomOccupancyErrorResponse apiError = new RoomOccupancyErrorResponse(ex.getMessage());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}