package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;

import java.util.List;

interface RoomBookingServiceInterface {
    RevenueMap calculateRevenue(List<Double> customers, AvailableRoom availableRoomList);
}
