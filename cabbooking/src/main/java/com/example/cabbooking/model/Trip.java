package com.example.cabbooking.model;

import jakarta.persistence.Entity;
import lombok.NonNull;
import lombok.ToString;


enum TripStatus {
    IN_PROGRESS,
    FINISHED
}
@Entity
@ToString
public class Trip {
    private Rider rider;
    private Cab cab;
    private TripStatus status;
    private Double price;
    private Location fromPoint;
    private Location toPoint;

    public Trip(
            @NonNull final Rider rider,
            @NonNull final Cab cab,
            @NonNull final Double price,
            @NonNull final Location fromPoint,
            @NonNull final Location toPoint) {
        this.rider = rider;
        this.cab = cab;
        this.price = price;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.status = TripStatus.IN_PROGRESS;
    }
    public void endTrip() {
        this.status = TripStatus.FINISHED;
    }
}
