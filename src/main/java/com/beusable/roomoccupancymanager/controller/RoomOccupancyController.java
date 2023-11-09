package com.beusable.roomoccupancymanager.controller;

import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import com.beusable.roomoccupancymanager.exception.RoomOccupancyErrorResponse;
import com.beusable.roomoccupancymanager.exception.RoomOccupancyException;
import com.beusable.roomoccupancymanager.service.RoomBookingServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/revenue")
public class RoomOccupancyController {
    private static final Logger log = LoggerFactory.getLogger(RoomOccupancyController.class);

    private final RoomBookingServiceInterface roomBookingService;

    public RoomOccupancyController(RoomBookingServiceInterface roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<?> getRevenue(@RequestBody AvailableRoom availableRoom) {
        log.debug("RoomOccupancyController.getRevenue: called");
        if (availableRoom == null) {
            return new ResponseEntity<>(new RoomOccupancyErrorResponse("AvailableRoom missing"), HttpStatus.BAD_REQUEST);

        }
        RevenueMap revenueMap = null;
        //hardcoded for POC, we could add an other endpoint what gives you the customers for the night, and we could save them into db
        List<Double> customers = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        try {
            revenueMap = roomBookingService.calculateRevenue(customers, availableRoom);
            return new ResponseEntity<>(revenueMap, HttpStatus.OK);
        } catch (RoomOccupancyException e) {
            return new ResponseEntity<>(new RoomOccupancyErrorResponse(e.getMessage()), e.getHttpStatus());
        }
    }
}
