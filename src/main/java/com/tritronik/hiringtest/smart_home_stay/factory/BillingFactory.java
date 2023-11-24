package com.tritronik.hiringtest.smart_home_stay.factory;

import com.tritronik.hiringtest.smart_home_stay.model.Billing;
import com.tritronik.hiringtest.smart_home_stay.model.Reservation;
import com.tritronik.hiringtest.smart_home_stay.model.SelectedAdditionalFacility;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillingFactory {
    public Billing createBilling(Reservation reservation) {

        calculateRoomPrice(reservation);


        Billing billing = new Billing();
        billing.setReservation(reservation);
        billing.setTotalAmount(
                calculateAll(reservation)
        );
        return billing;
    }

    public static double calculateAll(Reservation reservation) {
        double roomPrice = calculateRoomPrice(reservation);
        double facilitiesPrice = calculateFacilitiesPrice(reservation);

        return roomPrice + facilitiesPrice;
    }

    private static double calculateRoomPrice(Reservation reservation) {
        long days = getDays(reservation);
        return days * reservation.getRoom().getNightlyPrice();
    }

    private static long getDays(Reservation reservation) {
        LocalDate checkInDate = reservation.getCheckInDate();
        LocalDate checkOutDate = reservation.getCheckOutDate();
        long days = ChronoUnit.DAYS.between(checkOutDate, checkInDate);
        days = days == 0 ? 1 : Math.abs(days);
        return days;
    }

    private static double calculateFacilitiesPrice(Reservation reservation) {
        List<SelectedAdditionalFacility> facilities = reservation.getSelectedAdditionalFacilities();
        long days = getDays(reservation);

        return facilities.stream().map(facility -> {
            double nightlyPricePerItem = facility.getAdditionalFacility().getNightlyPricePerItem();
            return facility.getItemCounter() * days * nightlyPricePerItem;
        }).reduce(0d, Double::sum);
    }
}
