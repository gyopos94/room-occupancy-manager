package com.beusable.roomoccupancymanager.dto;

import java.util.Map;

public record AvailableRoom(Map<String, Integer> rooms) {
}
