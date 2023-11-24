package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.model.AdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.repository.AdditionalFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalFacilityServiceImpl implements AdditionalFacilityService {
    private final AdditionalFacilityRepository facilityRepository;

    @Autowired
    public AdditionalFacilityServiceImpl(AdditionalFacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<AdditionalFacility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    public AdditionalFacility getFacilityById(Long id) {
        return facilityRepository.findById(id).orElse(null);
    }

    @Override
    public AdditionalFacility saveAdditionalFacility(AdditionalFacility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public void deleteAdditionalFacility(Long id) {
        facilityRepository.deleteById(id);
    }

    @Override
    public List<AdditionalFacility> findAllById(List<Long> additionalFacilityIds) {
        return facilityRepository.findAllById(additionalFacilityIds);
    }
}
