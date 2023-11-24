package com.tritronik.hiringtest.smart_home_stay.controller;

import com.tritronik.hiringtest.smart_home_stay.adapter.SelectedAdditionalFacilityAdapter;
import com.tritronik.hiringtest.smart_home_stay.factory.BillingFactory;
import com.tritronik.hiringtest.smart_home_stay.model.Billing;
import com.tritronik.hiringtest.smart_home_stay.model.Reservation;
import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.model.dtos.CalculateRequest;
import com.tritronik.hiringtest.smart_home_stay.model.enums.ReservationStatus;
import com.tritronik.hiringtest.smart_home_stay.service.AdditionalFacilityService;
import com.tritronik.hiringtest.smart_home_stay.service.BillingService;
import com.tritronik.hiringtest.smart_home_stay.service.ReservationService;
import com.tritronik.hiringtest.smart_home_stay.service.SelectedAdditionalFacilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billings")
@Slf4j
public class BillingController {
    private final BillingService billingService;
    private final ReservationService reservationService;
    private final SelectedAdditionalFacilityService facilityService;
    private final AdditionalFacilityService additionalFacilityService;

    @Autowired
    public BillingController(BillingService billingService, ReservationService reservationService, SelectedAdditionalFacilityService facilityService, AdditionalFacilityService additionalFacilityService) {
        this.billingService = billingService;
        this.reservationService = reservationService;
        this.facilityService = facilityService;
        this.additionalFacilityService = additionalFacilityService;
    }


    @GetMapping
    private ResponseEntity<?> getAllBillings()
    {
        List<Billing> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBilling(@PathVariable Long id)
    {
        return ResponseEntity.ok( billingService.getBillingById(id));
    }

    @PutMapping("/calculate/{reservationId}")
    private ResponseEntity<?> calculate(@PathVariable Long reservationId,
                                        @RequestBody CalculateRequest calculateRequest) {
        try {
            List<Map<Long, Integer>> facilities = calculateRequest.getSelectedAdditionalFacilities();

            Billing billing = billingService.calculateBilling(reservationId, facilities);

            return ResponseEntity.ok(billing);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/pay/{billingId}")
    public ResponseEntity<String> payBilling(@PathVariable Long billingId) {
        // Retrieve Billing by ID
        Billing billing = billingService.getBillingById(billingId);

        if (billing == null) {
            return ResponseEntity.notFound().build();
        }

        // Update Reservation status to PAID
        Reservation reservation = billing.getReservation();
        if (reservation != null && reservation.getStatus() == ReservationStatus.OPENED) {
            reservation.setStatus(ReservationStatus.PAID);
            reservationService.updateReservation(reservation); // Implement this method in your service
            return ResponseEntity.ok("Billing paid successfully");
        } else {
            return ResponseEntity.badRequest().body("Billing is not opened or already paid");
        }
    }
}
