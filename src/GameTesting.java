import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// This is the class that distributes the countries, basically gameplay loop
public class GameTesting extends FullScreenUI {

    private String user;
    private Country[] countries;
    private int curIndex;

    private ArrayList<Integer> visitedIndices = new ArrayList<>();
    private ArrayList<Integer> randomizerStack = new ArrayList<>();


    // Randomizes the countries, and feeds it to the game play class.
    //
    public GameTesting(Screen previous, String username, String mode, String continent) throws IOException {

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setUndecorated(true);

        this.user = username;
        this.setVisible(true);
        this.requestFocus();
        //Static class that loads all the countries in a specific mode
        // For global it would be 50 country objects of type global
        this.countries = CountryList.getCountries(mode);
        this.curIndex = 0;

        startNextIteration();
    }

    // Game loop
    public void startNextIteration() {
        Country correctCountry;
        Country incorrectCountry1;
        Country incorrectCountry2;

        ArrayList<Integer> randomizerStack = new ArrayList<>();
        int totalCountries = countries.length;
        int index = 0;

        if (curIndex < countries.length) { // Check if there are more countries to display
            index = randomNumber(totalCountries);
            // loop until unique index is found
            while (visitedIndices.contains(index)) {
                index = randomNumber(totalCountries);
            }

            //All the countries visited, correct countries
            visitedIndices.add(index);

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

            try {
                // Create a new Gameplay instance for the current iteration
                this.setContentPane(new Gameplay(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2));
//                setContentPane(gameplay);
                revalidate(); // Refresh the UI
            } catch (IOException e) {
                e.printStackTrace(); // Handle the IOException appropriately
            }
            curIndex++;
        } else {
            // Handle end of game or loop back to the beginning if needed
        }
    }

    private int randomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

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

    public static void main(String[] args) throws IOException {
        new GameTesting(null, "jam", "Global Mode", null);

    }

}
