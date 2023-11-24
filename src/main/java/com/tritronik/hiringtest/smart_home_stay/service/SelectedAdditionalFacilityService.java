package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;

import java.util.List;

public interface SelectedAdditionalFacilityService {
    List<SelectedAdditionalFacility> getAllFacilities();
    SelectedAdditionalFacility getFacilityById(Long id);
    SelectedAdditionalFacility saveAdditionalFacility(SelectedAdditionalFacility facility);
    void saveBatch(List<SelectedAdditionalFacility> facility);

    List<SelectedAdditionalFacility> findAllById(List<Long> additionalFacilityIds);
}
