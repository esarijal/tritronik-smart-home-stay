package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;
import com.tritronik.hiringtest.smart_home_stay.repository.SelectedAdditionalFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedAdditionalFacilityServiceImpl implements SelectedAdditionalFacilityService {
    private final SelectedAdditionalFacilityRepository facilityRepository;

    @Autowired
    public SelectedAdditionalFacilityServiceImpl(SelectedAdditionalFacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<SelectedAdditionalFacility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    public SelectedAdditionalFacility getFacilityById(Long id) {
        return facilityRepository.findById(id).orElse(null);
    }

    @Override
    public SelectedAdditionalFacility saveAdditionalFacility(SelectedAdditionalFacility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public void saveBatch(List<SelectedAdditionalFacility> facility) {
        facilityRepository.saveAll(facility);
    }

    @Override
    public List<SelectedAdditionalFacility> findAllById(List<Long> additionalFacilityIds) {
        return facilityRepository.findAllById(additionalFacilityIds);
    }
}
