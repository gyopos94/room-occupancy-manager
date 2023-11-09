package com.beusable.roomoccupancymanager.controller;

import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import com.beusable.roomoccupancymanager.exception.RoomOccupancyException;
import com.beusable.roomoccupancymanager.service.RoomBookingServiceInterface;
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

    private final RoomBookingServiceInterface roomBookingService;

    public RoomOccupancyController(RoomBookingServiceInterface roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<RevenueMap> getRevenue(@RequestBody AvailableRoom availableRoom) {
        if (availableRoom == null) {
            throw new RoomOccupancyException("AvailableRoom is required", HttpStatus.BAD_REQUEST);
        }
        //hardcoded for POC, we could add an other endpoint what gives you the customers for the night, and we could save them into db
        List<Double> customers = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        RevenueMap revenueMap = roomBookingService.calculateRevenue(customers, availableRoom);
        if (revenueMap == null) {
            throw new RoomOccupancyException("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(revenueMap, HttpStatus.OK);
    }
}
