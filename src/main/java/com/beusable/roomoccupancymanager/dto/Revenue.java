package com.beusable.roomoccupancymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Revenue {
    private Integer usedRooms;
    private Double amount;
}