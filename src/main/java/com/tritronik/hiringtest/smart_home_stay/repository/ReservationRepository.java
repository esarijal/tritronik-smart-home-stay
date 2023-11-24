package com.tritronik.hiringtest.smart_home_stay.repository;

import com.tritronik.hiringtest.smart_home_stay.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
