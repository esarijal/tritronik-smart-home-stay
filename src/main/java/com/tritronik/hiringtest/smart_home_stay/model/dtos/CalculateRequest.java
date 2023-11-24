package com.tritronik.hiringtest.smart_home_stay.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CalculateRequest {
    @JsonProperty("selected_additional_facilities")
    List<Map<Long, Integer>> selectedAdditionalFacilities;
}
