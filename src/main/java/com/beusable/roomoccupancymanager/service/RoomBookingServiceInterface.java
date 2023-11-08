package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.controller.RoomOccupancyController;
import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;

import java.util.List;

public sealed interface RoomBookingServiceInterface permits RoomBookingService{
    RevenueMap calculateRevenue(List<Double> customers, AvailableRoom availableRoomList);
}
