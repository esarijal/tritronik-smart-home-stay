package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.adapter.SelectedAdditionalFacilityAdapter;
import com.tritronik.hiringtest.smart_home_stay.factory.BillingFactory;
import com.tritronik.hiringtest.smart_home_stay.model.Billing;
import com.tritronik.hiringtest.smart_home_stay.model.Reservation;
import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.model.enums.ReservationStatus;
import com.tritronik.hiringtest.smart_home_stay.repository.BillingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {
    private final BillingRepository billingRepository;
    private final ReservationService reservationService;
    private final AdditionalFacilityService additionalFacilityService;
    private final SelectedAdditionalFacilityService facilityService;

    @Autowired
    public BillingServiceImpl(BillingRepository billingRepository, ReservationService reservationService, AdditionalFacilityService additionalFacilityService, SelectedAdditionalFacilityService facilityService) {
        this.billingRepository = billingRepository;
        this.reservationService = reservationService;
        this.additionalFacilityService = additionalFacilityService;
        this.facilityService = facilityService;
    }

    @Override
    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    @Override
    public Billing getBillingById(Long id) {
        return billingRepository.findById(id).orElse(null);
    }

    @Override
    public Billing save(Billing billing) {
        return billingRepository.save(billing);
    }

    @Override
    public Billing findByReservation(Reservation reservation) {
        return billingRepository.findByReservation(reservation);
    }

    @Override
    public Billing calculateBilling(Long reservationId, List<Map<Long, Integer>> facilities) throws Exception {
        Reservation reservation = reservationService.findReservationById(reservationId);
        if(reservation.getStatus() != ReservationStatus.OPENED) {
            throw new Exception("Reservation is not OPENED, cannot continue recalculating");
        }
        Billing billing = findByReservation(reservation);

        // calculate current calculation
        SelectedAdditionalFacilityAdapter adapter = new SelectedAdditionalFacilityAdapter(additionalFacilityService);
        List<SelectedAdditionalFacility> selected = adapter.adaptFacilities(facilities);
        List<Long> facilitiesId = selected.stream().map(SelectedAdditionalFacility::getId).toList();

        // delete previous calculation
        reservation.getSelectedAdditionalFacilities().removeIf(f -> facilitiesId.contains(f.getId()));
        facilityService.saveBatch(selected);

        reservation.setSelectedAdditionalFacilities(selected);
        reservation = reservationService.updateReservation(reservation);

        double totalAmount = BillingFactory.calculateAll(reservation);
        billing.setTotalAmount(totalAmount);
        billing = save(billing);
        return billing;
    }
}
