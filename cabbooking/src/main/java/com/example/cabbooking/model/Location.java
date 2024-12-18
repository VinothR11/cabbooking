package com.example.cabbooking.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter  // Lombok annotation to automatically generate getters
@Setter  // Lombok annotation to automatically generate setters
public class Location {
    private Double x;
    private Double y;

    public Location() {
        // No-arg constructor for JPA
    }

    public Location(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double distance(Location location2) {
        return Math.sqrt(Math.pow(this.x - location2.x, 2) + Math.pow(this.y - location2.y, 2));
    }

    @Override
    public String toString() {
        return "Location{x=" + x + ", y=" + y + '}';
    }
}
