package com.tritronik.hiringtest.smart_home_stay.repository;

import com.tritronik.hiringtest.smart_home_stay.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
