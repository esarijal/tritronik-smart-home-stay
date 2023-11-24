package com.tritronik.hiringtest.smart_home_stay.adapter;

import com.tritronik.hiringtest.smart_home_stay.model.AdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.service.AdditionalFacilityService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelectedAdditionalFacilityAdapter {

    private final AdditionalFacilityService additionalFacilityService;

    public SelectedAdditionalFacilityAdapter(AdditionalFacilityService additionalFacilityService) {
        this.additionalFacilityService = additionalFacilityService;
    }

    public List<SelectedAdditionalFacility> adaptFacilities(List<Map<Long, Integer>> facilities) {
        return facilities.stream()
                .flatMap(map -> map.entrySet().stream())
                .map(this::adaptEntry)
                .collect(Collectors.toList());
    }

    private SelectedAdditionalFacility adaptEntry(Map.Entry<Long, Integer> entry) {
        Long additionalFacilityServiceId = entry.getKey();
        AdditionalFacility facility = additionalFacilityService.getFacilityById(additionalFacilityServiceId);
        return new SelectedAdditionalFacility(facility, entry.getValue());
    }
}
