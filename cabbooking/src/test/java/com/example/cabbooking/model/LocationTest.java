package com.example.cabbooking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testGetX() {
        Location location = new Location(10.0, 20.0);
        assertEquals(10.0, location.getX(), "X coordinate should be 10.0");
    }

    @Test
    void testGetY() {
        Location location = new Location(10.0, 20.0);
        assertEquals(20.0, location.getY(), "Y coordinate should be 20.0");
    }

    @Test
    void testSetX() {
        Location location = new Location(10.0, 20.0);
        location.setX(30.0);
        assertEquals(30.0, location.getX(), "X coordinate should be updated to 30.0");
    }

    @Test
    void testSetY() {
        Location location = new Location(10.0, 20.0);
        location.setY(40.0);
        assertEquals(40.0, location.getY(), "Y coordinate should be updated to 40.0");
    }

    @Test
    void testDistance() {
        Location location1 = new Location(0.0, 0.0);
        Location location2 = new Location(3.0, 4.0);
        assertEquals(5.0, location1.distance(location2), "Distance should be 5.0");
    }

    @Test
    void testToString() {
        Location location = new Location(10.0, 20.0);
        String expected = "Location{x=10.0, y=20.0}";
        assertEquals(expected, location.toString(), "toString output does not match");
    }
}
