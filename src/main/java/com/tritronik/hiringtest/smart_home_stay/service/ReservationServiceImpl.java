package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.factory.BillingFactory;
import com.tritronik.hiringtest.smart_home_stay.model.*;
import com.tritronik.hiringtest.smart_home_stay.model.enums.ReservationStatus;
import com.tritronik.hiringtest.smart_home_stay.model.enums.RoomStatus;
import com.tritronik.hiringtest.smart_home_stay.repository.BillingRepository;
import com.tritronik.hiringtest.smart_home_stay.repository.ReservationRepository;
import com.tritronik.hiringtest.smart_home_stay.repository.SelectedAdditionalFacilityRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final BillingFactory billingFactory;
    private final BillingRepository billingRepository;
    private final SelectedAdditionalFacilityRepository facilityRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, BillingFactory billingFactory, BillingRepository billingRepository, SelectedAdditionalFacilityRepository facilityRepository) {
        this.reservationRepository = reservationRepository;
        this.billingFactory = billingFactory;
        this.billingRepository = billingRepository;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation makeReservation(User user, Room room, List<SelectedAdditionalFacility> selectedAdditionalFacilities, LocalDate checkInDate, LocalDate checkoutDate) {
        facilityRepository.saveAll(selectedAdditionalFacilities);

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setSelectedAdditionalFacilities(selectedAdditionalFacilities);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkoutDate);
        reservation.setStatus(ReservationStatus.OPENED);

        reservation = reservationRepository.save(reservation);

        Billing billing = billingFactory.createBilling(reservation);
        billingRepository.save(billing);

        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void checkIn(Long reservationId) {
        reservationRepository.findById(reservationId).ifPresent(reservation -> {
            reservation.getRoom().setStatus(RoomStatus.BOOKED);
            reservation.setStatus(ReservationStatus.CHECKED_IN);
            reservationRepository.save(reservation);
        });
    }

    @Override
    public void checkOut(Long reservationId) {
        reservationRepository.findById(reservationId).ifPresent(reservation -> {
            reservation.getRoom().setStatus(RoomStatus.AVAILABLE); // TODO have to be DIRTY_CLEANING in real world simulation, but for the sake of simplicity I write directly into AVAILABLE
            reservation.setStatus(ReservationStatus.CHECKED_OUT);
            reservationRepository.save(reservation);
        });
    }
}
