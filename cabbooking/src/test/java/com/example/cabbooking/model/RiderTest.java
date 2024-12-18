import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiderTest {

    @Test
    void testConstructorAndGetters() {
        // Directly initialize the Rider object
        Rider rider = new Rider("RIDER123", "Alice");

        // Assert that the getter methods return correct values
        assertEquals("RIDER123", rider.getId(), "Rider ID should be 'RIDER123'");
        assertEquals("Alice", rider.getName(), "Rider name should be 'Alice'");
    }

    @Test
    void testSetters() {
        Rider rider = new Rider("RIDER123", "Alice");

        // Set new values using setter methods
        rider.setId("RIDER456");
        rider.setName("Bob");

        // Assert that the setter methods update the values correctly
        assertEquals("RIDER456", rider.getId(), "Rider ID should be updated to 'RIDER456'");
        assertEquals("Bob", rider.getName(), "Rider name should be updated to 'Bob'");
    }

    @Test
    void testToString() {
        Rider rider = new Rider("RIDER123", "Alice");

        // Get the string representation of the Rider object
        String riderString = rider.toString();

        // Assert that the toString method gives the expected format
        assertTrue(riderString.contains("Rider(id=RIDER123, name=Alice)"), "toString should include id and name");
    }
}
