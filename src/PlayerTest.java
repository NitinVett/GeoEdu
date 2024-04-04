import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player1;
    private Player player2;

    @Test
    void testGetNumGames() {
        player1 = new Player("testuser", "testpassword");

        player1.setNumGames(10);
        // Assuming the initial number of games played is 0
        assertEquals(10, player1.getNumGames());
    }

    @Test
    void testGetHighScore() {
        // Assuming the initial high score is 0
        player1 = new Player("testuser", "testpassword");

        player1.setHighScore(350);
        assertEquals(350, player1.getHighScore());
    }

    @Test
    void testGetAccuracy() {
        player1 = new Player("testuser", "testpassword");

        player1.setAccuracy(75.0f);

        assertEquals(75.0f, player1.getAccuracy());
    }

    @Test
    void testSetNumGames() {
        player1 = new Player("testuser", "testpassword");
        player1.setNumGames(5);
        assertEquals(5, player1.getNumGames());
    }

    @Test
    void testSetHighScore() {
        player1 = new Player("testuser", "testpassword");
        player1.setHighScore(1000);
        assertEquals(1000, player1.getHighScore());
    }

    @Test
    void testSetAccuracy() {
        player1 = new Player("testuser", "testpassword");
        player1.setAccuracy(75.0f);
        assertEquals(75.0f, player1.getAccuracy());
    }
}
