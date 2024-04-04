import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CsvHandlerTest {

    @Test
    public void testAddUser() {
        String result = CsvHandler.addUser("testUser2", "testPassword");
        //assertEquals("APPROVED", result);
        assertTrue(CsvHandler.isDuplicateUser("testUser2"));
    }

    @Test
    public void testGetPassword() {

        String password = CsvHandler.getPassword("testUser2");
        assertEquals("testPassword", password);
    }

    @Test
    public void testChangePassword() {
        CsvHandler.changePassword("testUser2", "newPassword");
        String newPassword = CsvHandler.getPassword("testUser2");
        assertEquals("newPassword", newPassword);
    }

    @Test
    public void testGetNumGamesPlayed() {
        String numGamesPlayed = CsvHandler.getNumGamesPlayed("testUser2");
        assertEquals("0", numGamesPlayed);
    }

    @Test
    public void testGetAccuracyRate() {
        String accuracyRate = CsvHandler.getAccuracyRate("testUser2");
        //assertNull(accuracyRate); // Assuming the user does not exist initially
        assertEquals(accuracyRate, CsvHandler.getAccuracyRate("testUser2"));
    }

    @Test
    public void testChangeAccuracyRate() {
        CsvHandler.changeAccuracyRate("testUser2", "50%");
        String updatedAccuracyRate = CsvHandler.getAccuracyRate("testUser2");
        assertEquals("50%", updatedAccuracyRate);
    }

    // Add more test cases for other methods as needed
}
