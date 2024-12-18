package com.example.cabbooking.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CabTest {

    @Test
    void testGetId() {
        Cab cab = new Cab("CAB123", "John Doe");
        assertEquals("CAB123", cab.getId(), "Cab ID should be 'CAB123'");
    }

    @Test
    void testGetDriverName() {
        Cab cab = new Cab("CAB123", "John Doe");
        assertEquals("John Doe", cab.getDriverName(), "Driver name should be 'John Doe'");
    }

    @Test
    void testSetIsAvailable() {
        Cab cab = new Cab("CAB123", "John Doe");
        cab.setIsAvailable(false);
        assertFalse(cab.getIsAvailable(), "Cab should not be available after setting to false");
    }

    @Test
    void testToString() {
        Cab cab = new Cab("CAB123", "John Doe");
        String expected = "Cab{id='CAB123', driverName='John Doe', currentLocation=null, isAvailable=true}";
        assertEquals(expected, cab.toString(), "toString output does not match");
    }
}
