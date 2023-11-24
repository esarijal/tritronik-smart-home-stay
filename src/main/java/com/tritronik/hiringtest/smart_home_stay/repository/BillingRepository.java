package com.tritronik.hiringtest.smart_home_stay.repository;

import com.tritronik.hiringtest.smart_home_stay.model.Billing;
import com.tritronik.hiringtest.smart_home_stay.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    Billing findByReservation(Reservation reservation);
}
