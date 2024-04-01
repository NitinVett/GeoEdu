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
                    timeLeft--; // Decrease time left
                    CsvHandler.changeListOfCountry(user.getUsername(),this.toString());
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
        sb.append(";timeLeft:").append(timeLeft)
                .append(";lives:").append(lives)
                .append(";type:").append(type);

        // Assuming Country has a meaningful toString() method or a unique identifier
        sb.append(";correctCountry:").append(correctCountry != null ? correctCountry.getName() : "null")
                .append(";incorrectCountry1:").append(incorrectCountry1 != null ? incorrectCountry1.getName() : "null")
                .append(";incorrectCountry2:").append(incorrectCountry2 != null ? incorrectCountry2.getName() : "null");

        return sb.toString();
    }

    public void loadFile(String saveString){
        // Split the string by semicolon to get each field
        String[] keyValuePairs = saveString.split(";");
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
                // Add cases for other fields if necessary
            }
        }
        this.setContentPane(new TimedMode(this, null, user, correctCountry, incorrectCountry1, incorrectCountry2,timeLeft));

    }
    public void startNextIteration() {
        if(type.equals("Timed")){
            startNextIterationTimed();
        } else if(type.equals("Marathon")){
            startNextIterationMarathon();
        }
        else if (type.equals("Exploration")){
            startNextIterationExploration();
        }
        CsvHandler.changeListOfCountry(user.getUsername(),this.toString());
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
        Player a = new Player("jam","1");
        String gameData = CsvHandler.getListOfCountry(a.getUsername());
        GameTesting z = new GameTesting(null, a, "Global Mode",null,"Exploration");
        //z.loadFile(gameData);
    }
}