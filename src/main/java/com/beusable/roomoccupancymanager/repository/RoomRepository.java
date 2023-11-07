package com.beusable.roomoccupancymanager.repository;

import com.beusable.roomoccupancymanager.dao.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {}
