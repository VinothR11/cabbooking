package com.example.cabbooking.service;

import com.example.cabbooking.model.Rider;
import com.example.cabbooking.model.Rider;
import com.example.cabbooking.model.Trip;
import com.example.cabbooking.strategies.CabMatchingStrategy;
import com.example.cabbooking.strategies.PricingStrategy;
import org.junit.com.example.cabbooking.exception.NoCabsAvailableException;
import com.example.cabbooking.exception.TripNotFoundException;
import com.example.cabbooking.model.Cab;
import com.example.cabbooking.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class TripManagerTest {
    private TripsManager tripsManager;
    private CabsManager cabsManager;
    private RidersManager ridersManager;

    @BeforeEach
    void setUp() {
        // Initialize the managers and pass mock objects for testing
        cabsManager = new CabsManager();
        ridersManager = new RidersManager();
        tripsManager = new TripsManager(cabsManager, ridersManager, new CabMatchingStrategy(), new PricingStrategy());
    }

    @Test
    void testCreateTripSuccessfully() {
        // Setup rider and locations
        Rider rider = new Rider("RIDER001", "John Doe");
        Location pickupLocation = new Location(1.0, 1.0);
        Location dropLocation = new Location(2.0, 2.0);

        // Create a new trip
        tripsManager.createTrip(rider, pickupLocation, dropLocation);

        // Verify that the trip was added to the history
        List<Trip> trips = tripsManager.tripHistory(rider);
        assertNotNull(trips);
        assertEquals(1, trips.size(), "There should be one trip in the history");
        assertEquals(rider, trips.get(0).getRider(), "Rider should match");
    }

    @Test
    void testCreateTripThrowsNoCabsAvailableException() {
        Rider rider = new Rider("RIDER001", "John Doe");
        Location pickupLocation = new Location(1.0, 1.0);
        Location dropLocation = new Location(2.0, 2.0);

        // Try to create a trip when no cabs are available
        assertThrows(NoCabsAvailableException.class, () -> {
            tripsManager.createTrip(rider, pickupLocation, dropLocation);
        }, "Should throw NoCabsAvailableException when no cabs are available");
    }

    @Test
    void testEndTripSuccessfully() {
        // Setup cab and rider
        Rider rider = new Rider("RIDER001", "John Doe");
        Location pickupLocation = new Location(1.0, 1.0);
        Location dropLocation = new Location(2.0, 2.0);
        Cab cab = new Cab("CAB001", "Driver1");

        // Create a trip and associate it with the cab
        tripsManager.createTrip(rider, pickupLocation, dropLocation);

        // Verify that a trip is created
        List<Trip> trips = tripsManager.tripHistory(rider);
        Trip trip = trips.get(0);

        // Assign the current trip to the cab
        cab.setCurrentTrip(trip);

        // End the trip
        tripsManager.endTrip(cab);

        // Verify that the trip is ended
        assertNull(cab.getCurrentTrip(), "Cab should have no current trip after ending the trip");
    }

    @Test
    void testEndTripThrowsTripNotFoundException() {
        Cab cab = new Cab("CAB001", "Driver1");

        // Try to end a trip when there is no active trip
        assertThrows(TripNotFoundException.class, () -> {
            tripsManager.endTrip(cab);
        }, "Should throw TripNotFoundException when no active trip is found for the cab");
    }

                     }
