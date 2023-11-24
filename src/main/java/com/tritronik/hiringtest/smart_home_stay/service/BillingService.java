package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.adapter.SelectedAdditionalFacilityAdapter;
import com.tritronik.hiringtest.smart_home_stay.factory.BillingFactory;
import com.tritronik.hiringtest.smart_home_stay.model.Billing;
import com.tritronik.hiringtest.smart_home_stay.model.Reservation;
import com.tritronik.hiringtest.smart_home_stay.model.Room;
import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;

import java.util.List;
import java.util.Map;

public interface BillingService {
    List<Billing> getAllBillings();
    Billing getBillingById(Long id);

    Billing save(Billing billing);

    Billing findByReservation(Reservation reservation);

    Billing calculateBilling(Long reservationId, List<Map<Long, Integer>> facilities) throws Exception;
}
