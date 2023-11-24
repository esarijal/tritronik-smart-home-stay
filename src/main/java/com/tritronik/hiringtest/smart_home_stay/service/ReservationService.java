package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.model.*;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    Reservation findReservationById(Long id);
    Reservation makeReservation(User user, Room room, List<SelectedAdditionalFacility> selectedAdditionalFacilities, LocalDate checkInDate, LocalDate checkoutDate);
    Reservation updateReservation(Reservation reservation);
    void checkIn(Long reservationId);
    void checkOut(Long reservationId);
}
