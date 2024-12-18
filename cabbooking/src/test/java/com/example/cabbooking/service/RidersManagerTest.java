package com.example.cabbooking.Service;


import com.example.cabbooking.service.RidersManager;
import com.example.cabbooking.exception.RiderAlreadyExistsException;
import com.example.cabbooking.exception.RiderNotFoundException;
import com.example.cabbooking.model.Rider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RiderManagerTest {
    private RidersManager ridersManager;

    @BeforeEach
    public void setUp() {
        // Initialize the RidersManager before each test
        ridersManager = new RidersManager();
    }

    @Test
    public void testCreateRiderSuccessfully() {
        // Create a new Rider
        Rider newRider = new Rider("RIDER001", "John Doe");

        // Add the new rider using createRider method
        ridersManager.createRider(newRider);

        // Retrieve the rider and verify it was added successfully
        Rider retrievedRider = ridersManager.getRider("RIDER001");

        // Assert that the rider's details are correct
        assertNotNull(retrievedRider, "Rider should not be null");
        assertEquals("RIDER001", retrievedRider.getId(), "Rider ID should match");
        assertEquals("John Doe", retrievedRider.getDriverName(), "Rider name should match");
    }

    @Test
    public void testCreateRiderThrowsRiderAlreadyExistsException() {
        // Create and add a rider
        Rider newRider = new Rider("RIDER001", "John Doe");
        ridersManager.createRider(newRider);

        // Try adding the same rider again, should throw RiderAlreadyExistsException
        Rider duplicateRider = new Rider("RIDER001", "Jane Doe");

        assertThrows(RiderAlreadyExistsException.class, () -> {
            ridersManager.createRider(duplicateRider);
        }, "Should throw RiderAlreadyExistsException for duplicate rider ID");
    }

    @Test
    public void testGetRiderSuccessfully() {
        // Create a new rider and add it
        Rider newRider = new Rider("RIDER001", "John Doe");
        ridersManager.createRider(newRider);

        // Retrieve the rider by ID
        Rider retrievedRider = ridersManager.getRider("RIDER001");

        // Verify that the rider's details are correct
        assertNotNull(retrievedRider, "Rider should not be null");
        assertEquals("RIDER001", retrievedRider.getId(), "Rider ID should match");
        assertEquals("John Doe", retrievedRider.getDriverName(), "Rider name should match");
    }

    @Test
    public void testGetRiderThrowsRiderNotFoundException() {
        // Try to retrieve a rider who does not exist
        assertThrows(RiderNotFoundException.class, () -> {
            ridersManager.getRider("RIDER002");
        }, "Should throw RiderNotFoundException for non-existent rider");
    }
                     }
