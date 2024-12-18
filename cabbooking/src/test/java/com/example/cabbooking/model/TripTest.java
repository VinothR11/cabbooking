import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {

    @Test
    void testConstructorAndInitialValues() {
        // Create mock objects for the constructor
        Rider rider = new Rider("RIDER123", "Alice");
        Cab cab = new Cab("CAB001", "John");
        Location fromPoint = new Location(10.0, 20.0);
        Location toPoint = new Location(30.0, 40.0);
        Double price = 100.0;

        // Initialize the Trip object
        Trip trip = new Trip(rider, cab, price, fromPoint, toPoint);

        // Assert initial values from the constructor
        assertNotNull(trip);
        assertEquals(TripStatus.IN_PROGRESS, trip.getStatus(), "Trip status should be 'IN_PROGRESS'");
        assertEquals(100.0, trip.getPrice(), "Price should be 100.0");
        assertEquals(fromPoint, trip.getFromPoint(), "From point should match");
        assertEquals(toPoint, trip.getToPoint(), "To point should match");
    }
}
