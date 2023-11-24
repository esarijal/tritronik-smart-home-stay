package com.tritronik.hiringtest.smart_home_stay.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class ReservationRequest {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("room_id")
    private Long roomId;
    @JsonProperty("selected_additional_facilities")
    private List<Map<Long, Integer>> selectedAdditionalFacilities;
    @JsonProperty("check_in_date")
    private LocalDate checkInDate;
    @JsonProperty("check_out_date")
    private LocalDate checkOutDate;

}
