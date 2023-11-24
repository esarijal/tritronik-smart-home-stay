package com.tritronik.hiringtest.smart_home_stay.controller;

import com.tritronik.hiringtest.smart_home_stay.model.Room;
import com.tritronik.hiringtest.smart_home_stay.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        List<Room> allRooms = roomService.getAllRooms();
        return ResponseEntity.ok(allRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id){
        Room found = roomService.getRoomById(id);
        if(found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }
}
