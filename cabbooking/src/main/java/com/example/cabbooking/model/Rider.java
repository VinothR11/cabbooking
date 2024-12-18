package com.example.cabbooking.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;

@Getter
@Setter
@Entity
public class Rider {
    private String id;
    private String riderName;

    // Parameterized constructor
    public Rider(String id, String riderName) {
        this.id = id;
        this.riderName = riderName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Rider{" +
                "id='" + id + '\'' +
                ", riderName='" + riderName + '\'' +
                '}';
    }
}
