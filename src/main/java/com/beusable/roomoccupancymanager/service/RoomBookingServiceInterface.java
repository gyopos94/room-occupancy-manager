package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dto.AvailableRoomList;
import com.beusable.roomoccupancymanager.dto.RevenueMap;

import java.util.List;

public interface RoomBookingServiceInterface {
    RevenueMap calculateRevenue(List<Double> customers, AvailableRoomList availableRoomList);
}
