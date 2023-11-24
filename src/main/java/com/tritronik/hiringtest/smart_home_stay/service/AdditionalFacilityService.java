package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.model.AdditionalFacility;

import java.util.List;

public interface AdditionalFacilityService {
    List<AdditionalFacility> getAllFacilities();
    AdditionalFacility getFacilityById(Long id);
    AdditionalFacility saveAdditionalFacility(AdditionalFacility facility);
    void deleteAdditionalFacility(Long id);

    List<AdditionalFacility> findAllById(List<Long> additionalFacilityIds);
}
