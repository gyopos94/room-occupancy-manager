package com.beusable.roomoccupancymanager.util;

import com.beusable.roomoccupancymanager.dao.Room;
import com.beusable.roomoccupancymanager.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public DatabaseInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Room room1 = new Room();
        room1.setId(1L);
        room1.setType("Premium");
        room1.setMinPrice(100);

        Room room2 = new Room();
        room2.setId(2L);
        room2.setType("Economy");
        room2.setMinPrice(0);

        roomRepository.save(room1);
        roomRepository.save(room2);
    }
}
