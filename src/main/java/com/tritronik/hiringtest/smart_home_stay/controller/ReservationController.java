package com.tritronik.hiringtest.smart_home_stay.controller;

import com.tritronik.hiringtest.smart_home_stay.adapter.SelectedAdditionalFacilityAdapter;
import com.tritronik.hiringtest.smart_home_stay.model.*;
import com.tritronik.hiringtest.smart_home_stay.model.dtos.ReservationRequest;
import com.tritronik.hiringtest.smart_home_stay.model.enums.ReservationStatus;
import com.tritronik.hiringtest.smart_home_stay.service.AdditionalFacilityService;
import com.tritronik.hiringtest.smart_home_stay.service.ReservationService;
import com.tritronik.hiringtest.smart_home_stay.service.RoomService;
import com.tritronik.hiringtest.smart_home_stay.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final RoomService roomService;
    private final AdditionalFacilityService additionalFacilityService;
    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService, RoomService roomService, AdditionalFacilityService additionalFacilityService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.roomService = roomService;
        this.additionalFacilityService = additionalFacilityService;
    }


    @GetMapping
    public ResponseEntity<?> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findReservationById(id));
    }

    @PostMapping("/make")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            validateReservationRequest(reservationRequest);

            User user = userService.findById(reservationRequest.getUserId());
            if(user == null) throw new Exception("User Not Found");
            Room room = roomService.getRoomById(reservationRequest.getRoomId());
            if(room == null) throw new Exception("Room Not Found");

            List<Map<Long, Integer>> facilities = reservationRequest.getSelectedAdditionalFacilities();
            SelectedAdditionalFacilityAdapter adapter = new SelectedAdditionalFacilityAdapter(additionalFacilityService);
            List<SelectedAdditionalFacility> selected = adapter.adaptFacilities(facilities);

            LocalDate checkInDate = reservationRequest.getCheckInDate();
            LocalDate checkOutDate = reservationRequest.getCheckOutDate();
            Reservation reservation = reservationService.makeReservation(user, room, selected, checkInDate, checkOutDate);

            return ResponseEntity.ok(reservation);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/checkin/{reservationId}")
    public ResponseEntity<?> checkin(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);

        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        if (reservation.getStatus() == ReservationStatus.PAID) {
            reservation.setStatus(ReservationStatus.CHECKED_IN);
            reservationService.updateReservation(reservation); // Implement this method in your service
            return ResponseEntity.ok("Checked In successfully");
        } else {
            return ResponseEntity.badRequest().body("Cannot Check In because Billing is not PAID");
        }
    }

    @PutMapping("/checkout/{reservationId}")
    public ResponseEntity<?> checkout(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);

        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        if (reservation.getStatus() == ReservationStatus.CHECKED_IN) {
            reservation.setStatus(ReservationStatus.CHECKED_OUT);
            reservationService.updateReservation(reservation); // Implement this method in your service
            return ResponseEntity.ok("Checked Out successfully");
        } else {
            return ResponseEntity.badRequest().body("Cannot Check Out because not check in");
        }
    }

    private void validateReservationRequest(ReservationRequest reservationRequest) throws Exception {
        if (reservationRequest.getUserId() == null || reservationRequest.getRoomId() == null ||
                reservationRequest.getCheckInDate() == null || reservationRequest.getCheckOutDate() == null) {
            throw new Exception("Invalid reservation request. All fields must be provided.");
        }
    }
}
