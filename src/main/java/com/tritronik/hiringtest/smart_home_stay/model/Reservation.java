package com.tritronik.hiringtest.smart_home_stay.model;

import com.tritronik.hiringtest.smart_home_stay.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

    @Data
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "reservations")
    public class Reservation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private User user;
        @ManyToOne
        private Room room;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "reservation_selected_additional_facilities",
                joinColumns = @JoinColumn(name = "reservation_id"),
                inverseJoinColumns = @JoinColumn(name = "selected_additional_facility_id"))
        private List<SelectedAdditionalFacility> selectedAdditionalFacilities;

        private LocalDate checkInDate;
        private LocalDate checkOutDate;

        @Enumerated(EnumType.STRING)
        private ReservationStatus status;
    }
