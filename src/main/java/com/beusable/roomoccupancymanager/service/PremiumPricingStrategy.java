package com.beusable.roomoccupancymanager.service;

import com.beusable.roomoccupancymanager.dao.Room;
import com.beusable.roomoccupancymanager.dto.AvailableRoom;
import com.beusable.roomoccupancymanager.dto.Revenue;
import com.beusable.roomoccupancymanager.dto.RevenueMap;
import com.beusable.roomoccupancymanager.exception.RoomOccupancyException;
import com.beusable.roomoccupancymanager.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public final class PremiumPricingStrategy implements PricingStrategy {
    private final RoomRepository roomRepository;

    public PremiumPricingStrategy(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Calculate the revenue generated from a list of customers based on room availability and prices.
     *
     * @param customers        A list of customer offers, where each offer is a double representing the amount the customer is willing to pay.
     * @param availableRoomMap The available room map containing the number of premium and economy rooms.
     * @return A RevenueMap object that represents the revenue generated from premium and economy rooms.
     */
    public RevenueMap calculateRevenue(List<Double> customers, AvailableRoom availableRoomMap) {

        if (CollectionUtils.isEmpty(customers) || CollectionUtils.isEmpty(availableRoomMap.rooms()))
            throw new RoomOccupancyException("No customers or availableRoomMap", HttpStatus.INTERNAL_SERVER_ERROR);

        // Retrieve room types from the repository
        List<Room> roomTypes = roomRepository.findAll();
        if (CollectionUtils.isEmpty(roomTypes))
            throw new RoomOccupancyException("No rooms available", HttpStatus.INTERNAL_SERVER_ERROR);


        // Get the minimum price for premium and economy rooms, later if we have more type of rooms, we can adjust the logic
        int premiumMinPrice = getMinPriceForRoomType(roomTypes, "premium");

        // Separate customers into premium and economy lists
        List<Double> premiumCustomers = new ArrayList<>();
        List<Double> economyCustomers = new ArrayList<>();
        for (Double number : customers) {
            if (number >= premiumMinPrice) {
                premiumCustomers.add(number);
            } else {
                economyCustomers.add(number);
            }
        }

        // Get the number of available premium and economy rooms
        int premiumRooms = availableRoomMap.rooms().get("premium");
        int economyRooms = availableRoomMap.rooms().get("economy");

        // Sort the premium and economy customer lists in descending order
        Collections.sort(premiumCustomers, Collections.reverseOrder());
        Collections.sort(economyCustomers, Collections.reverseOrder());

        // Calculate the number of premium rooms occupied
        int premiumOccupied = Math.min(premiumRooms, premiumCustomers.size());

        // Create a list of the top premium customers
        List<Double> topPremiumCustomers = premiumCustomers.subList(0, premiumOccupied);

        // Handle upgrades from economy to premium rooms
        int isUpgrade = premiumRooms - premiumOccupied;
        premiumOccupied = handleUpgrades(economyCustomers, economyRooms, premiumOccupied, topPremiumCustomers, isUpgrade);

        // Calculate the number of economy rooms occupied
        int economyOccupied = Math.min(economyRooms, economyCustomers.size());

        // Create lists of the top economy customers
        List<Double> topEconomyCustomers = economyCustomers.subList(0, economyOccupied);

        // Calculate premium and economy revenues
        double premiumRevenue = calculateRevenueFromCustomers(topPremiumCustomers);
        double economyRevenue = calculateRevenueFromCustomers(topEconomyCustomers);

        // Create a revenue map with the results
        RevenueMap revenueMap = getRevenueMap(premiumOccupied, economyOccupied, premiumRevenue, economyRevenue);

        return revenueMap;
    }

    // Helper method to create a revenue map
    private RevenueMap getRevenueMap(int premiumOccupied, int economyOccupied, double premiumRevenue, double economyRevenue) {
        Map<String, Revenue> revenueMapTemp = new HashMap<>();
        revenueMapTemp.put("premium", new Revenue(premiumOccupied, premiumRevenue));
        revenueMapTemp.put("economy", new Revenue(economyOccupied, economyRevenue));

        RevenueMap revenueMap = new RevenueMap(revenueMapTemp);
        return revenueMap;
    }

    // Helper method to handle room upgrades
    private int handleUpgrades(List<Double> economyCustomers, int economyRooms, int premiumOccupied,
                               List<Double> topPremiumCustomers, int isUpgrade) {
        if (isUpgrade > 0 && economyCustomers.size() > economyRooms) {
            int upgradeEconomyCustomers = Math.min(isUpgrade, economyCustomers.size());
            List<Double> topEconomyCustomers = economyCustomers.subList(0, upgradeEconomyCustomers);
            topPremiumCustomers.addAll(topEconomyCustomers);
            premiumOccupied += upgradeEconomyCustomers;

            // Clear the upgraded economy rooms
            economyCustomers.subList(0, upgradeEconomyCustomers).clear();
        }
        return premiumOccupied;
    }

    // Helper method to get the minimum price for a room type
    private int getMinPriceForRoomType(List<Room> rooms, String roomType) {
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(roomType)) {
                return room.getMinPrice();
            }
        }
        return 0;
    }

    // Helper method to calculate revenue from customers
    private double calculateRevenueFromCustomers(List<Double> customers) {
        return customers.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
