package com.tritronik.hiringtest.smart_home_stay.controller;

import com.tritronik.hiringtest.smart_home_stay.model.AdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.model.Room;
import com.tritronik.hiringtest.smart_home_stay.service.AdditionalFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {
    private final AdditionalFacilityService facilityService;

    @Autowired
    public FacilityController(AdditionalFacilityService facilityService) {
        this.facilityService = facilityService;
    }


    @GetMapping
    public ResponseEntity<?> getAllFacilities() {
        return ResponseEntity.ok(facilityService.getAllFacilities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable Long id) {
        AdditionalFacility facility = facilityService.getFacilityById(id);
        if(facility == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facility);
    }
}
