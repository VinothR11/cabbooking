import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.cabbooking.model.Location;
import com.example.cabbooking.model.Rider;
import com.example.cabbooking.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)  // Required for Spring TestContext Framework
@WebMvcTest(RidersController.class)  // This annotation tests the controller layer only
class RidersControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Injects MockMvc to simulate HTTP requests

    @MockBean
    private RidersManager ridersManager;  // Mock RidersManager service

    @MockBean
    private TripsManager tripsManager;  // Mock TripsManager service

    @Autowired
    private RidersController ridersController;  // Injects the controller

    @Test
    void testRegisterRiderSuccess() throws Exception {
        // Arrange
        String riderId = "RIDER123";
        String riderName = "Alice";

        // Act
        mockMvc.perform(post("/api/riders/register")
                        .param("riderId", riderId)
                        .param("riderName", riderName))  // Sending the POST request
                .andExpect(status().isOk())  // Asserts that status is OK (200)
                .andExpect(content().string("Rider registered successfully."));  // Asserts that response message is correct

        // Assert: Verify interaction with the service
        verify(ridersManager, times(1)).createRider(any(Rider.class));
    }

    @Test
    void testRegisterRiderFailureNullInput() throws Exception {
        // Arrange
        String riderId = null;
        String riderName = null;

        // Act & Assert
        mockMvc.perform(post("/api/riders/register")
                        .param("riderId", riderId)
                        .param("riderName", riderName))  // Sending the POST request with invalid params
                .andExpect(status().isBadRequest());  // Asserts that it returns BadRequest (400)
    }

    @Test
    void testBookTripSuccess() throws Exception {
        // Arrange
        String riderId = "RIDER123";
        Double sourceX = 10.0;
        Double sourceY = 20.0;
        Double destX = 30.0;
        Double destY = 40.0;

        Rider mockRider = new Rider(riderId, "Alice");

        when(ridersManager.getRider(riderId)).thenReturn(mockRider);  // Mocking rider retrieval

        // Act
        mockMvc.perform(post("/api/riders/book")
                        .param("riderId", riderId)
                        .param("sourceX", sourceX.toString())
                        .param("sourceY", sourceY.toString())
                        .param("destX", destX.toString())
                        .param("destY", destY.toString()))  // Sending the POST request with trip details
                .andExpect(status().isOk())  // Asserts that status is OK (200)
                .andExpect(content().string("Trip booked successfully."));  // Asserts that response message is correct

        // Assert: Verify interaction with the service
        verify(tripsManager, times(1)).createTrip(any(Rider.class), any(Location.class), any(Location.class));
    }

    @Test
    void testBookTripFailureRiderNotFound() throws Exception {
        // Arrange
        String riderId = "RIDER123";
        Double sourceX = 10.0;
        Double sourceY = 20.0;
        Double destX = 30.0;
        Double destY = 40.0;

        when(ridersManager.getRider(riderId)).thenReturn(null);  // Simulating rider not found

        // Act & Assert
        mockMvc.perform(post("/api/riders/book")
                        .param("riderId", riderId)
                        .param("sourceX", sourceX.toString())
                        .param("sourceY", sourceY.toString())
                        .param("destX", destX.toString())
                        .param("destY", destY.toString()))  // Sending the POST request with invalid rider
                .andExpect(status().isNotFound());  // Asserts that it returns NotFound (404)
    }

    @Test
    void testFetchTripHistorySuccess() throws Exception {
        // Arrange
        String riderId = "RIDER123";
        Rider mockRider = new Rider(riderId, "Alice");
        Trip mockTrip = new Trip(mockRider, new Location(10.0, 20.0), new Location(30.0, 40.0));

        when(ridersManager.getRider(riderId)).thenReturn(mockRider);  // Mocking rider retrieval
        when(tripsManager.tripHistory(mockRider)).thenReturn(Arrays.asList(mockTrip));  // Mocking trip history

        // Act
        mockMvc.perform(get("/api/riders/history")
                        .param("riderId", riderId))  // Sending the GET request for trip history
                .andExpect(status().isOk())  // Asserts that status is OK (200)
                .andExpect(jsonPath("$[0].sourceX").value(10.0))  // Asserts the sourceX of the trip
                .andExpect(jsonPath("$[0].sourceY").value(20.0))  // Asserts the sourceY of the trip
                .andExpect(jsonPath("$[0].destinationX").value(30.0))  // Asserts the destinationX of the trip
                .andExpect(jsonPath("$[0].destinationY").value(40.0));  // Asserts the destinationY of the trip

        // Assert: Verify interaction with the service
        verify(tripsManager, times(1)).tripHistory(mockRider);
    }

    @Test
    void testFetchTripHistoryFailureRiderNotFound() throws Exception {
        // Arrange
        String riderId = "RIDER123";

        when(ridersManager.getRider(riderId)).thenReturn(null);  // Simulating rider not found

        // Act & Assert
        mockMvc.perform(get("/api/riders/history")
                        .param("riderId", riderId))  // Sending the GET request with invalid rider
                .andExpect(status().isNotFound());  // Asserts that it returns NotFound (404)
    }
}
