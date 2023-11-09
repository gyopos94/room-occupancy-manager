package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dao.Room;
import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import com.beusable.roomoccupancymanager.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PremiumPricingStrategyTest {

    @InjectMocks
    private PremiumPricingStrategy premiumPricingStrategy;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3, 738, 167.99, 3, 3",
            "6, 4, 1054, 189.99, 7, 5",
            "2, 4, 583, 189.99, 2, 7",
            "7, 1, 1153.99, 45, 7, 1"
    })
    public void testCalculateRevenue(
            int premiumRooms,
            int economyRooms,
            double expectedPremiumRevenue,
            double expectedEconomyRevenue,
            int initialPremiumRooms,
            int initialEconomyRooms) {
        // Arrange
        List<Double> customers = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);

        Map<String, Integer> availableRoomMap = new HashMap<>();
        availableRoomMap.put("premium", initialPremiumRooms);
        availableRoomMap.put("economy", initialEconomyRooms);
        AvailableRoom availableRoom = new AvailableRoom(availableRoomMap);

        Room room1 = new Room();
        room1.setId(1L);
        room1.setType("Premium");
        room1.setMinPrice(100);

        Room room2 = new Room();
        room2.setId(2L);
        room2.setType("Economy");
        room2.setMinPrice(0);

        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

        // Act
        RevenueMap revenueMap = premiumPricingStrategy.calculateRevenue(customers, availableRoom);

        // Assert
        verify(roomRepository, times(1)).findAll();
        assertEquals(2, revenueMap.revenues().size());
        assertEquals(premiumRooms, revenueMap.revenues().get("premium").getUsedRooms());
        assertEquals(economyRooms, revenueMap.revenues().get("economy").getUsedRooms());
        assertEquals(expectedPremiumRevenue, revenueMap.revenues().get("premium").getAmount());
        assertEquals(expectedEconomyRevenue, revenueMap.revenues().get("economy").getAmount());
    }
}
