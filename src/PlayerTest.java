import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Player} class. This test class verifies the functionality
 * of the player's game statistics management, including the number of games played,
 * high score, and accuracy percentage.
 */
public class PlayerTest {

    /**
     * player object for test
     */
    private Player player1;

    /**
     * Tests the retrieval of the number of games played by a player.
     * This method checks if the {@link Player#getNumGames()} method accurately reflects
     * the number of games set using {@link Player#setNumGames(int)}.
     */
    @Test
    void testGetNumGames() {
        player1 = new Player("testuser", "testpassword");

        player1.setNumGames(10);
        // Verifying that the number of games played is correctly reported
        assertEquals(10, player1.getNumGames());
    }

    /**
     * Tests the retrieval of the player's high score.
     * This method verifies if the {@link Player#getHighScore()} method correctly returns
     * the high score value previously set using {@link Player#setHighScore(int)}.
     */
    @Test
    void testGetHighScore() {
        player1 = new Player("testuser", "testpassword");

        player1.setHighScore(350);
        // Confirming the high score is accurately maintained
        assertEquals(350, player1.getHighScore());
    }

    /**
     * Tests the accuracy percentage retrieval for a player.
     * This method checks if the {@link Player#getAccuracy()} method correctly returns
     * the accuracy percentage previously set with {@link Player#setAccuracy(float)}.
     */
    @Test
    void testGetAccuracy() {
        player1 = new Player("testuser", "testpassword");

        player1.setAccuracy(75.0f);
        // Ensuring the accuracy is correctly updated and retrieved
        assertEquals(75.0f, player1.getAccuracy());
    }

    /**
     * Tests setting the number of games played by a player.
     * This method verifies that the player's number of games played can be updated
     * and correctly reflected by the {@link Player#getNumGames()} method.
     */
    @Test
    void testSetNumGames() {
        player1 = new Player("testuser", "testpassword");
        player1.setNumGames(5);
        // Confirming that setting the number of games updates it correctly
        assertEquals(5, player1.getNumGames());
    }

    /**
     * Tests setting the player's high score.
     * This method ensures that the player's high score can be updated and is
     * accurately retrieved using {@link Player#getHighScore()}.
     */
    @Test
    void testSetHighScore() {
        player1 = new Player("testuser", "testpassword");
        player1.setHighScore(1000);
        // Verifying the high score is updated and retrieved accurately
        assertEquals(1000, player1.getHighScore());
    }

    /**
     * Tests setting the accuracy percentage for a player.
     * This method checks that the player's accuracy can be updated and confirms
     * the new value is accurately returned by {@link Player#getAccuracy()}.
     */
    @Test
    void testSetAccuracy() {
        player1 = new Player("testuser", "testpassword");
        player1.setAccuracy(75.0f);
        // Ensuring the accuracy percentage is correctly updated and retrieved
        assertEquals(75.0f, player1.getAccuracy());
    }
}
