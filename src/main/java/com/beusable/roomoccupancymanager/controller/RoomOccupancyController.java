package com.beusable.roomoccupancymanager.controller;

import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import com.beusable.roomoccupancymanager.exception.RoomOccupancyException;
import com.beusable.roomoccupancymanager.service.RoomBookingServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Calculate Revenue",
            description = "Calculate revenue based on available rooms and a list of customers. The input should be provided as an `AvailableRoom` object, and the response will be a `RevenueMap` detailing the revenue generated for different room types."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Revenue calculation successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RevenueMap.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - The input is missing or invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomOccupancyException.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomOccupancyException.class)
                    )
            )
    })
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
