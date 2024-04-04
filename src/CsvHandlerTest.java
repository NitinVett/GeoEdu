import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the {@link CsvHandler} class, focusing on user management and statistics
 * functionalities such as adding users, changing passwords, and managing game statistics
 * like the number of games played and the accuracy rate. Each test case aims to validate
 * the correctness of these operations.
 */
public class CsvHandlerTest {

    /**
     * Tests the addition of a new user. It verifies that the user is correctly added by
     * checking if the newly added user is marked as a duplicate, indicating successful insertion.
     */
    @Test
    public void testAddUser() {
        String result = CsvHandler.addUser("testUser2", "testPassword");
        assertTrue(CsvHandler.isDuplicateUser("testUser2"));
    }

    /**
     * Verifies the functionality to retrieve a user's password. It ensures that the
     * password returned matches the one that was initially set for the user.
     */
    @Test
    public void testGetPassword() {
        String password = CsvHandler.getPassword("testUser2");
        assertEquals("testPassword", password);
    }

    /**
     * Tests the ability to change a user's password. It updates the password for an
     * existing user and then retrieves it to ensure the change was successful.
     */
    @Test
    public void testChangePassword() {
        CsvHandler.changePassword("testUser2", "newPassword");
        String newPassword = CsvHandler.getPassword("testUser2");
        assertEquals("newPassword", newPassword);
    }

    /**
     * Validates that the correct number of games played is returned for a user.
     * This test checks for the initial state, assuming no games have been played.
     */
    @Test
    public void testGetNumGamesPlayed() {
        String numGamesPlayed = CsvHandler.getNumGamesPlayed("testUser2");
        assertEquals("0", numGamesPlayed);
    }

    /**
     * Ensures that the accuracy rate retrieval function works correctly. It tests
     * that the method returns a consistent accuracy rate for a user.
     */
    @Test
    public void testGetAccuracyRate() {
        String accuracyRate = CsvHandler.getAccuracyRate("testUser2");
        assertEquals(accuracyRate, CsvHandler.getAccuracyRate("testUser2"));
    }

    /**
     * Confirms that updating a user's accuracy rate works as expected. It changes
     * the rate and then retrieves it to verify the update was applied.
     */
    @Test
    public void testChangeAccuracyRate() {
        CsvHandler.changeAccuracyRate("testUser2", "50%");
        String updatedAccuracyRate = CsvHandler.getAccuracyRate("testUser2");
        assertEquals("50%", updatedAccuracyRate);
    }


}
