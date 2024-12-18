import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {

    @Test
    void testOk() {
        // Act
        PostResponse response = PostResponse.ok();

        // Assert
        assertEquals("ok", response.getStatus(), "Status should be 'ok'");
        assertNull(response.getMessage(), "Message should be null for 'ok' response");
    }

    @Test
    void testError() {
        // Act
        String errorMessage = "Something went wrong";
        PostResponse response = PostResponse.error(errorMessage);

        // Assert
        assertEquals("error", response.getStatus(), "Status should be 'error'");
        assertEquals(errorMessage, response.getMessage(), "Message should match the error message");
    }
}
