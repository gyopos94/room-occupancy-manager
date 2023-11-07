package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dao.Room;
import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.AvailableRoomList;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import com.beusable.roomoccupancymanager.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomBookingServiceTest {

    @InjectMocks
    private RoomBookingService roomBookingService;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        setupRoomRepository();
    }

    private void setupRoomRepository() {
        Room room1 = new Room(1L, "premium", 100);
        Room room2 = new Room(2L, "economy", 0);
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));
    }

    @Test
    public void testCalculateRevenue() {
        testRevenueCalculation(
                Arrays.asList(new AvailableRoom("premium", 3), new AvailableRoom("economy", 3)),
                2, 3, 3, 738, 167.99
        );

        testRevenueCalculation(
                Arrays.asList(new AvailableRoom("premium", 7), new AvailableRoom("economy", 5)),
                2, 6, 4, 1054, 189.99
        );

        testRevenueCalculation(
                Arrays.asList(new AvailableRoom("premium", 2), new AvailableRoom("economy", 7)),
                2, 2, 4, 583, 189.99
        );

        testRevenueCalculation(
                Arrays.asList(new AvailableRoom("premium", 7), new AvailableRoom("economy", 1)),
                2, 7, 1, 1153, 45.99
        );
    }

    private void testRevenueCalculation(
            List<AvailableRoom> availableRooms,
            int expectedSize,
            int expectedPremiumUsedRooms,
            int expectedEconomyUsedRooms,
            double expectedPremiumAmount,
            double expectedEconomyAmount
    ) {
        // Arrange
        List<Double> customers = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        AvailableRoomList availableRoomList = new AvailableRoomList(availableRooms);

        // Act
        RevenueMap revenueMap = roomBookingService.calculateRevenue(customers, availableRoomList);

        // Assert
        verify(roomRepository, times(1)).findAll();
        assertEquals(expectedSize, revenueMap.revenues().size());
        assertEquals(expectedPremiumUsedRooms, revenueMap.revenues().get("premium").usedRooms());
        assertEquals(expectedEconomyUsedRooms, revenueMap.revenues().get("economy").usedRooms());
        assertEquals(expectedPremiumAmount, revenueMap.revenues().get("premium").amount());
        assertEquals(expectedEconomyAmount, revenueMap.revenues().get("economy").amount());
    }
}
