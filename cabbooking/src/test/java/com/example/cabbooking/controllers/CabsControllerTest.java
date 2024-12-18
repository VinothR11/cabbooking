import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.cabbooking.controllers.CabsController;
import com.example.cabbooking.service.CabsManager;
import com.example.cabbooking.service.TripsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)  // Required to enable Spring TestContext Framework
@WebMvcTest(CabsController.class)  // This annotation is used to test only the controller layer
class CabsControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Automatically injects MockMvc

    @MockBean
    private CabsManager cabsManager;  // Mocking CabsManager service

    @MockBean
    private TripsManager tripsManager;  // Mocking TripsManager service

    @Autowired
    private CabsController cabsController;  // Automatically injects the controller

    @Test
    void testRegisterCabSuccess() throws Exception {
        // Arrange
        String cabId = "CAB123";
        String driverName = "John Doe";

        // Act
        mockMvc.perform(post("/api/cabs/register")
                        .param("cabId", cabId)
                        .param("driverName", driverName))  // Sends POST request with params
                .andExpect(status().isOk())  // Asserts that the status is OK (200)
                .andExpect(content().string("Cab registered successfully."));  // Asserts response body content

        // Assert: Verify interaction with the service
        verify(cabsManager, times(1)).createCab(any(Cab.class));  // Verifies that the createCab method was called once
    }

    @Test
    void testRegisterCabFailureNullInput() throws Exception {
        // Arrange
        String cabId = null;
        String driverName = null;

        // Act & Assert
        mockMvc.perform(post("/api/cabs/register")
                        .param("cabId", cabId)
                        .param("driverName", driverName))  // Sends POST request with invalid params
                .andExpect(status().isBadRequest());  // Asserts that it returns BadRequest (400)
    }

    @Test
    void testUpdateCabLocationSuccess() throws Exception {
        // Arrange
        String cabId = "CAB123";
        Double newX = 10.0;
        Double newY = 20.0;

        // Act
        mockMvc.perform(post("/api/cabs/location/update")
                        .param("cabId", cabId)
                        .param("newX", newX.toString())
                        .param("newY", newY.toString()))  // Sends POST request with location params
                .andExpect(status().isOk())  // Asserts that the status is OK (200)
                .andExpect(content().string("Cab location updated successfully."));  // Asserts response body content

        // Assert: Verify interaction with the service
        verify(cabsManager, times(1)).updateCabLocation(eq(cabId), any(Location.class));
    }

    @Test
    void testUpdateCabLocationFailureNullCoordinates() throws Exception {
        // Arrange
        String cabId = "CAB123";
        Double newX = null;
        Double newY = null;

        // Act & Assert
        mockMvc.perform(post("/api/cabs/location/update")
                        .param("cabId", cabId)
                        .param("newX", newX.toString())
                        .param("newY", newY.toString()))  // Sends POST request with invalid coordinates
                .andExpect(status().isBadRequest());  // Asserts that it returns BadRequest (400)
    }

    @Test
    void testUpdateCabAvailabilitySuccess() throws Exception {
        // Arrange
        String cabId = "CAB123";
        Boolean newAvailability = true;

        // Act
        mockMvc.perform(post("/api/cabs/availability/update")
                        .param("cabId", cabId)
                        .param("newAvailability", newAvailability.toString()))  // Sends POST request with availability params
                .andExpect(status().isOk())  // Asserts that the status is OK (200)
                .andExpect(content().string("Cab availability updated successfully."));  // Asserts response body content

        // Assert: Verify interaction with the service
        verify(cabsManager, times(1)).updateCabAvailability(cabId, newAvailability);
    }

    @Test
    void testUpdateCabAvailabilityFailureNullCabId() throws Exception {
        // Arrange
        String cabId = null;
        Boolean newAvailability = true;

        // Act & Assert
        mockMvc.perform(post("/api/cabs/availability/update")
                        .param("cabId", cabId)
                        .param("newAvailability", newAvailability.toString()))  // Sends POST request with invalid cabId
                .andExpect(status().isBadRequest());  // Asserts that it returns BadRequest (400)
    }

    @Test
    void testEndTripSuccess() throws Exception {
        // Arrange
        String cabId = "CAB123";
        Cab mockCab = new Cab(cabId, "John Doe");

        when(cabsManager.getCab(cabId)).thenReturn(mockCab);  // Mock the behavior of cabsManager.getCab()

        // Act
        mockMvc.perform(post("/api/cabs/trip/end")
                        .param("cabId", cabId))  // Sends POST request to end the trip
                .andExpect(status().isOk())  // Asserts that the status is OK (200)
                .andExpect(content().string("Trip ended successfully."));  // Asserts response body content

        // Assert: Verify interaction with the service
        verify(tripsManager, times(1)).endTrip(mockCab);
        verify(cabsManager, times(1)).getCab(cabId);
    }

    @Test
    void testEndTripFailureCabNotFound() throws Exception {
        // Arrange
        String cabId = "CAB123";

        when(cabsManager.getCab(cabId)).thenReturn(null);  // Simulate cab not found

        // Act & Assert
        mockMvc.perform(post("/api/cabs/trip/end")
                        .param("cabId", cabId))  // Sends POST request to end the trip
                .andExpect(status().isNotFound());  // Asserts that it returns NotFound (404)
    }
}
