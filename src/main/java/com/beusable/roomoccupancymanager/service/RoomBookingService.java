package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class RoomBookingService implements RoomBookingServiceInterface {

    private final PricingStrategy premiumPricingStrategy;

    public RoomBookingService(PricingStrategy premiumPricingStrategy) {
        this.premiumPricingStrategy = premiumPricingStrategy;
    }

    @Override
    public RevenueMap calculateRevenue(List<Double> customers, AvailableRoom availableRoomMap) {
        return premiumPricingStrategy.calculateRevenue(customers, availableRoomMap);
    }
}