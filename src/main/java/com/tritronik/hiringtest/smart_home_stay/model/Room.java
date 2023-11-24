package com.tritronik.hiringtest.smart_home_stay.model;

import com.tritronik.hiringtest.smart_home_stay.model.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType type;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private double nightlyPrice;
}
