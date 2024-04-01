import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class GameTesting extends FullScreenUI implements Serializable {

    private int lives = 3;
    Timer timer;
    private Player user;
    private Country[] countries;
    private int curIndex;
    int timeLeft = 60;
    private ArrayList<Integer> visitedIndices = new ArrayList<>();
    private ArrayList<Integer> randomizerStack = new ArrayList<>();
    Country correctCountry;
    Country incorrectCountry1;
    Country incorrectCountry2;
    String type;


    // Randomizes the countries, and feeds it to the game play class.
    //
    public GameTesting(Screen previous, Player user, String mode, String continent,String type) throws IOException {
        this.user = user;
        this.type = type;
        //Static class that loads all the countries in a specific mode
        // For global it would be 50 country objects of type global
        this.countries = CountryList.getCountries(mode, continent);
        this.curIndex = 0;

        if(type.equals("Timed")) {
            timer = new Timer(1000, e -> {
                if (timeLeft > 0 && curIndex < countries.length) {
                    System.out.println(timeLeft);
                    timeLeft--; // Decrease time left

//          setContentPane(gameplay);
                    revalidate(); // Refresh the UI


                    repaint(); // Request a repaint to update the timer display
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer
                    // You can add what should happen when the timer reaches 0
                }
            });

            timer.start(); // Start the countdown
        }
        startNextIteration();
    }


    // Game loop
    public void startNextIterationMarathon() {
        ArrayList<Integer> randomizerStack = new ArrayList<>();
        int totalCountries = countries.length;
        int index;

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
            revalidate();
            this.setContentPane(new MarathonMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2,lives));
            curIndex++;
        } else {

        }
    }
    public void startNextIterationExploration() {
        ArrayList<Integer> randomizerStack = new ArrayList<>();
        int totalCountries = countries.length;
        int index;
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
            revalidate();
            this.setContentPane(new ExplorationMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2));
            curIndex++;
        } else {

        }
    }

    public int getTime(){
        return timeLeft;
    }
    public void reduceLives(){
        lives =lives-1;
    }

    public void startNextIterationTimed() {
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
        this.setContentPane(new TimedMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2,timeLeft));
        curIndex++;
    }
    public void startNextIteration() {
        if(type.equals("Timed")){
            startNextIterationTimed();
        } else if(type.equals("Marathon")){
            startNextIterationMarathon();
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
        new GameTesting(null, new Player("jam","1"), "Continental Mode", "Europe","Marathon");
    }
}