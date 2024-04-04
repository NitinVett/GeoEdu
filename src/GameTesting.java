import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Represents and manages the core gameplay logic, including handling different game modes,
 * tracking player progress, and managing game states. It supports timed, exploration, and marathon
 * modes, among potentially others, by coordinating between various screen states and game data.
 */
public class GameTesting implements Serializable {

    /** The number of lives a player has during the game session. */
    private int lives = 3;

    /** A timer for timed gameplay, decreasing the time left and updating the UI accordingly. */
    Timer timer;

    /** The current player participating in the game session. */
    private Player user;

    /** An array holding all the country objects relevant to the current game mode. */
    private Country[] countries;

    /** The current index within the countries array, indicating the next country to be used. */
    private int curIndex;

    /** The amount of time left in seconds for timed game modes. */
    int timeLeft = 60;

    /** A list of indices from the countries array that have already been visited/used. */
    private ArrayList<Integer> visitedIndices = new ArrayList<>();

    /** A temporary stack used for randomizing country selection without repetition. */
    private ArrayList<Integer> randomizerStack = new ArrayList<>();

    /** The country object representing the correct answer for the current question. */
    Country correctCountry;

    /** The first incorrect country option. */
    Country incorrectCountry1;

    /** The second incorrect country option. */
    Country incorrectCountry2;

    /** The type of the current game (e.g., "Timed", "Exploration"). */
    String type;

    /** The main application window. */
    FullScreenUI frame;

    /** The mode of the game, affecting which countries are loaded (e.g., "Global", "Continental"). */
    String mode;

    /** The current gameplay screen being displayed. */
    GameplayScreen currentGame;

    /** The specific continent selected for the game, applicable in continental mode. */
    String continent;

    /** The total number of guesses made by the player in the current game. */
    private int numGuesses;

    /** The total number of correct guesses made by the player in the current game. */
    private int correctGuesses = 0;

    /**
     * Initializes a new GameTesting instance with specified game parameters and settings.
     *
     * @param frame The main application window.
     * @param user The current player.
     * @param mode The game mode ("Global", "Continental", etc.).
     * @param continent The selected continent for the game, if applicable.
     * @param type The type of game (e.g., "Timed", "Marathon").
     */
    public GameTesting(FullScreenUI frame, Player user, String mode, String continent,String type) {

        this.continent = continent;
        this.user = user;
        this.type = type;
        this.frame = frame;
        this.mode = mode;
        //Static class that loads all the countries in a specific mode
        // For global it would be 50 country objects of type global

        this.countries = CountryList.getCountries(mode, this.continent);
        this.curIndex = 0;

        if(type.equals("Timed")) {
            timer = new Timer(1000, e -> {
                if (timeLeft > 0 && curIndex < countries.length) {
                    timeLeft--; // Decrease time left
                    saveFile();
                    frame.revalidate(); // Refresh the UI
                    frame.repaint(); // Request a repaint to update the timer display
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer
                    endGame();
                }
            });
            timer.start(); // Start the countdown
        }

    }
    // Additional methods such as startNextIterationMarathon, startNextIterationExploration, etc.,
    // handle the logic for progressing through the game, loading new questions, and transitioning between screens based on the game state.

    /**
     * Sets the total number of guesses made by the player.
     * This method updates the count of guesses a player has made, which can be used
     * for tracking player performance and potentially adjusting game difficulty or providing feedback.
     *
     * @param numGuesses The new total number of guesses made by the player.
     */
    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    /**
     * Gets the total number of guesses made by the player in the current game session.
     * This can be used to evaluate player performance or for statistical purposes, among other uses.
     *
     * @return The total number of guesses made by the player.
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    /**
     * Sets the number of correct guesses made by the player.
     * This is particularly useful for tracking the player's success rate and can be used
     * to adjust game mechanics or provide feedback on performance.
     *
     * @param correctGuesses The new total number of correct guesses made by the player.
     */
    public void setCorrectGuesses(int correctGuesses){
        this.correctGuesses = correctGuesses;
    }

    /**
     * Retrieves the number of correct guesses made by the player in the current game session.
     * This information can be valuable for evaluating the player's knowledge or skill level
     * in the context of the game.
     *
     * @return The total number of correct guesses made by the player.
     */
    public int getCorrectGuesses() {
        return correctGuesses;
    }

    // Game loop
    /**
     * Initiates the next iteration of the marathon game mode, loading new countries and updating the game state.
     *
     * @param load Indicates whether to load an existing game state or start fresh.
     */
    public void startNextIterationMarathon(boolean load) {
        ArrayList<Integer> randomizerStack = new ArrayList<>();
        int totalCountries = countries.length;
        int index=0;

        if (curIndex < countries.length) { // Check if there are more countries to display
            index = randomNumber(totalCountries);
            // loop until unique index is found
            while (visitedIndices.contains(index)) {
                index = randomNumber(totalCountries);
            }

            //All the countries visited, correct countries
            visitedIndices.add(index);
            if(!load) {
                // index is a rand number, so the country being chosen also random
                correctCountry = countries[index];

                // By keeping track of the random number, we can avoid duplicates and ensure randomness.
                int random = getRandomIntWithAvoidance(totalCountries, index);
                incorrectCountry1 = countries[random];
                randomizerStack.add(random);

                while (randomizerStack.contains(random)) {
                    random = getRandomIntWithAvoidance(totalCountries, index);
                }
                incorrectCountry2 = countries[random];
                randomizerStack.clear();
            }
            frame.revalidate();
            currentGame = new MarathonMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2);

            frame.setContentPane(currentGame);
            curIndex++;
        }else {
            endGame();
        }
    }

    /**
     * Initiates the next iteration of the exploration game mode, with similar functionality tailored for exploration.
     *
     * @param load Indicates whether to load an existing game state or start fresh.
     */
    public void startNextIterationExploration(boolean load) {
        ArrayList<Integer> randomizerStack = new ArrayList<>();
        int totalCountries = countries.length;
        int index=0;

        if (curIndex < countries.length) { // Check if there are more countries to display
            index = randomNumber(totalCountries);
            // loop until unique index is found
            while (visitedIndices.contains(index)) {
                index = randomNumber(totalCountries);
            }

            //All the countries visited, correct countries
            visitedIndices.add(index);
            if(!load) {
                // index is a rand number, so the country being chosen also random
                correctCountry = countries[index];

                // By keeping track of the random number, we can avoid duplicates and ensure randomness.
                int random = getRandomIntWithAvoidance(totalCountries, index);
                incorrectCountry1 = countries[random];
                randomizerStack.add(random);

                while (randomizerStack.contains(random)) {
                    random = getRandomIntWithAvoidance(totalCountries, index);
                }
                incorrectCountry2 = countries[random];
                randomizerStack.clear();
            }
            frame.revalidate();
            currentGame = new ExplorationMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2);

            frame.setContentPane(currentGame);

            curIndex++;
        }else {
            endGame();
        }
    }

    /**
     * Initiates the next iteration of the timed game mode, applying specific logic for timed gameplay.
     *
     * @param load Indicates whether to load an existing game state or start fresh.
     */
    public void startNextIterationTimed(boolean load) {
        ArrayList<Integer> randomizerStack = new ArrayList<>();
        int totalCountries = countries.length;
        int index = 0;

        // Initialize the timer
        index = randomNumber(totalCountries);
        // loop until unique index is found
        while (visitedIndices.contains(index)) {
            index = randomNumber(totalCountries);
        }

        //All the countries visited, correct countries
        visitedIndices.add(index);
        if(!load) {
            // index is a rand number, so the country being chosen also random
            correctCountry = countries[index];

            // By keeping track of the random number, we can avoid duplicates and ensure randomness.
            int random = getRandomIntWithAvoidance(totalCountries, index);
            incorrectCountry1 = countries[random];
            randomizerStack.add(random);

            while (randomizerStack.contains(random)) {
                random = getRandomIntWithAvoidance(totalCountries, index);
            }
            incorrectCountry2 = countries[random];

            randomizerStack.clear();
        }
        currentGame = new TimedMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2,timeLeft);
        frame.setContentPane(currentGame);
        saveFile();

        curIndex++;
    }

    /**
     * Returns the remaining time in the timed game mode.
     *
     * @return The time left in seconds.
     */
    public int getTime(){
        return timeLeft;
    }

    /**
     * Reduces the player's lives by one, applicable in game modes where lives are used.
     */
    public void reduceLives(){
        lives =lives-1;

    }

    /**
     * Returns the number of lives the player has left.
     *
     * @return The current number of lives.
     */
    public int getLives(){
        return lives;
    }

    /**
     * Converts the current game state to a string representation for saving.
     *
     * @return A string representing the current game state.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Convert visitedIndices list to a string representation
        sb.append("visitedIndices:");
        for (Integer index : visitedIndices) {
            sb.append(index).append("-");
        }
        // Remove the last comma
        if (!visitedIndices.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }

        // Append other fields
        sb.append(";type:").append(type)
                .append(";mode:").append(mode)
                .append(";continent:").append(continent)
                .append(";timeLeft:").append(timeLeft)
                .append(";lives:").append(lives)
                .append(";numGuesses:").append(numGuesses)
                .append(";correctGuesses:").append(correctGuesses);

        // Assuming Country has a meaningful toString() method or a unique identifier
        sb.append(";correctCountry:").append(correctCountry != null ? correctCountry.getName() : "null")
                .append(";incorrectCountry1:").append(incorrectCountry1 != null ? incorrectCountry1.getName() : "null")
                .append(";incorrectCountry2:").append(incorrectCountry2 != null ? incorrectCountry2.getName() : "null");
        currentGame.saveVars(sb);
        return sb.toString();
    }

    /**
     * Saves the current game state to a file or persistent storage.
     */
    public void saveFile(){
        CsvHandler.changeListOfCountry(user.getUsername(),this.toString());
    }

    /**
     * Loads a game state from a given string representation, updating the current state.
     *
     * @param saveString The string representation of a saved game state.
     */
    public void loadFile(String saveString) {

        // Split the string by semicolon to get each field
        String[] keyValuePairs = saveString.split(";");
        boolean showFlag = false;
        boolean showHint = false;
        for (String pair : keyValuePairs) {
            // Split each pair by the first occurrence of colon to separate key and value
            String[] entry = pair.split(":", 2);
            String key = entry[0];
            String value = entry.length > 1 ? entry[1] : "";

            switch (key) {
                case "visitedIndices":
                    // Assuming visitedIndices is cleared or initialized elsewhere
                    if (!value.isEmpty()) {
                        for (String indexStr : value.split("-")) {
                            this.visitedIndices.add(Integer.parseInt(indexStr));
                        }
                    }
                    break;
                case "timeLeft":
                    this.timeLeft = Integer.parseInt(value);
                    break;
                case "lives":
                    this.lives = Integer.parseInt(value);
                    break;
                case "type":
                    this.type = value;
                    break;
                case "mode":
                    this.mode = value;
                    break;
                case "correctCountry":
                    // Assuming you have a way to reconstruct Country objects from their string representation
                    this.correctCountry = new Country(value);
                    break;
                case "incorrectCountry1":
                    this.incorrectCountry1 = new Country(value);
                    break;
                case "incorrectCountry2":
                    this.incorrectCountry2 = new Country(value);
                    break;
                case "showFlag":
                    showFlag = Boolean.parseBoolean(value);
                    break;
                case "showHint":
                    showHint = Boolean.parseBoolean(value);
                    break;
                case "numGuesses":
                    numGuesses = Integer.parseInt(value);
                case "correctGuesses":
                    correctGuesses = Integer.parseInt(value);
                    // Add cases for other fields if necessary
            }
        }
        newGame(true);


        currentGame.flagWasClicked = showFlag;
        if(showFlag){
            currentGame.showFlag();
        }
        currentGame.hintWasClicked = showHint;
        if(showHint){
            currentGame.showHints();
        }

        currentGame.repaint();





    }

    /**
     * Initiates a new game round based on the current game type (Exploration, Timed, Marathon).
     * This method decides which iteration method to call based on the game type and whether it's loading a game.
     *
     * @param load A boolean indicating whether the game is being loaded from a saved state.
     */
    public void newGame(boolean load) {

        switch (type) {
            case "Exploration" -> startNextIterationExploration(load);
            case "Timed" -> startNextIterationTimed(load);
            case "Marathon" -> startNextIterationMarathon(load);
        }
        saveFile();
    }

    /**
     * Ends the current game session, performing any necessary cleanup, saving final stats,
     * and transitioning to the game over or statistics screen.
     */
    public void endGame(){
        user.setGameData("None");

        float totalPercentage = 0;
        if(numGuesses != 0) {
            totalPercentage = ((float) correctGuesses/(float) numGuesses)*100;
        }

        totalPercentage += user.getNumGames() * (user.getAccuracy());

        user.setAccuracy((totalPercentage/ (user.getNumGames()+1)));

        user.setNumGames(user.getNumGames() + 1);


        frame.setContentPane(new StatScreen(frame, null, user));
    }

    /**
     * Generates a random number within a specified range (0 to max), exclusive of max.
     * This utility method is used to select random countries from the available list.
     *
     * @param max The upper bound for the random number generation.
     * @return A randomly generated integer within the specified range.
     */
    private int randomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    /**
     * Generates a random integer within a specified range, excluding a specific value.
     * This method ensures that the random number generated is not equal to the value to avoid,
     * which is useful for selecting incorrect country options without repetition.
     *
     * @param max The upper bound for the random number generation.
     * @param avoidValue The value to be excluded from the random number generation.
     * @return A randomly generated integer within the specified range, not equal to avoidValue.
     */

    private int getRandomIntWithAvoidance(int max, int avoidValue) {
        if (0 > max) {
            throw new IllegalArgumentException("Invalid range: min must be less than or equal to max.");
        }

        int randomNum;
        do {
            randomNum = ThreadLocalRandom.current().nextInt(0, max);
        } while (randomNum == avoidValue);
        return randomNum;

    }


}