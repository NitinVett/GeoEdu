import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class GameTesting implements Serializable {

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
    FullScreenUI frame;
    String mode;
    GameplayScreen currentGame;
    String continent;
    // Randomizes the countries, and feeds it to the game play class.
    //
    public GameTesting(FullScreenUI frame, Player user, String mode, String continent,String type) {
        System.out.println(mode);
        this.continent = continent;
        this.user = user;
        this.type = type;
        this.frame = frame;
        this.mode = mode;
        //Static class that loads all the countries in a specific mode
        // For global it would be 50 country objects of type global
        System.out.println(mode);
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
                    frame.setContentPane(new StatScreen(frame,null,user));
                }
            });
            timer.start(); // Start the countdown
        }

    }


    // Game loop
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
        }
    }

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
            System.out.println("heeere");
            frame.setContentPane(currentGame);

            curIndex++;
        }
    }

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
    public int getTime(){
        return timeLeft;
    }
    public void reduceLives(){
        lives =lives-1;

    }
    public int getLives(){
        return lives;
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
        sb.append(";type:").append(type)
                .append(";mode:").append(mode)
                .append(";continent:").append(continent)
                .append(";timeLeft:").append(timeLeft)
                .append(";lives:").append(lives);

        // Assuming Country has a meaningful toString() method or a unique identifier
        sb.append(";correctCountry:").append(correctCountry != null ? correctCountry.getName() : "null")
                .append(";incorrectCountry1:").append(incorrectCountry1 != null ? incorrectCountry1.getName() : "null")
                .append(";incorrectCountry2:").append(incorrectCountry2 != null ? incorrectCountry2.getName() : "null");
        currentGame.saveVars(sb);
        return sb.toString();
    }

    public void saveFile(){
        CsvHandler.changeListOfCountry(user.getUsername(),this.toString());
    }

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
                // Add cases for other fields if necessary
            }
        }
        newGame(true);

        System.out.println(showFlag + "2");
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
    public void newGame(boolean load) {

        switch (type) {
            case "Exploration" -> startNextIterationExploration(load);
            case "Timed" -> startNextIterationTimed(load);
            case "Marathon" -> startNextIterationMarathon(load);
        }
        saveFile();
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


}