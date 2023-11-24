package com.tritronik.hiringtest.smart_home_stay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "selected_additional_facilities")
public class SelectedAdditionalFacility {

    public SelectedAdditionalFacility(AdditionalFacility additionalFacility, int itemCounter) {
        this.additionalFacility = additionalFacility;
        this.itemCounter = itemCounter;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "additional_facility_id")
    private AdditionalFacility additionalFacility;

    private int itemCounter;

}
