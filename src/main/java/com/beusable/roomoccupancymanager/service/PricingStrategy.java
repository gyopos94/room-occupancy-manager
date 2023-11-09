package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;

import java.util.List;

public interface PricingStrategy {
    RevenueMap calculateRevenue(List<Double> customers, AvailableRoom availableRoomMap);
}