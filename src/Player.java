/**
 * The Player class represents a player user in the game.
 * It extends the User class and provides methods to interact with user data
 * stored in a CSV file using the CsvHandler class.
 */
public class Player extends User {

    /**
     * Constructs a new Player with the given username and password.
     * This constructor also adds the player to the CSV file using CsvHandler.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     */
    public Player(String username, String password) {
        super(username, password);
        System.out.println(CsvHandler.addUser(username, password));
    }

    /**
     * Retrieves the number of games played by the player.
     *
     * @return The number of games played by the player.
     */
    public int getNumGames() {
        return Integer.parseInt(CsvHandler.getNumGamesPlayed(getUsername()));
    }

    /**
     * Retrieves the high score of the player.
     *
     * @return The high score of the player.
     */
    public int getHighScore() {
        return Integer.parseInt(CsvHandler.getHighScore(getUsername()));
    }

    /**
     * Retrieves the accuracy rate of the player.
     *
     * @return The accuracy rate of the player.
     */
    public float getAccuracy() {
        return Float.parseFloat(CsvHandler.getAccuracyRate(getUsername()));
    }

    /**
     * Sets the number of games played by the player.
     *
     * @param numGames The number of games played by the player.
     */
    public void setNumGames(int numGames) {
        CsvHandler.changeNumGamesPlayed(getUsername(), Integer.toString(numGames));
    }

    /**
     * Sets the high score of the player.
     *
     * @param highScore The high score of the player.
     */
    public void setHighScore(int highScore) {
        if (highScore >= 0) {
            CsvHandler.changeHighScore(getUsername(), Integer.toString(highScore));
        }
    }

    /**
     * Sets the accuracy rate of the player.
     *
     * @param accuracy The accuracy rate of the player.
     */
    public void setAccuracy(float accuracy) {
        CsvHandler.changeAccuracyRate(getUsername(), Float.toString(accuracy));
    }

    /**
     * Sets the game data for the player.
     *
     * @param gameData The game data to set for the player.
     */
    public void setGameData(String gameData) {
        CsvHandler.changeListOfCountry(getUsername(), gameData);
    }

    /**
     * Retrieves the game data for the player.
     *
     * @return The game data for the player.
     */
    public String getGameData() {
        return CsvHandler.getListOfCountry(getUsername());
    }
}
