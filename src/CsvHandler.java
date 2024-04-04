import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * This class handles interactions with a CSV file containing user data for a game.
 * It provides methods for reading, updating, and managing user information.
 */
public class CsvHandler {

    /** The file path of the CSV file storing user data. */
    private static final String CSV_FILE_PATH = "database.csv";
    /**
     * getting the file path
     */
    static Path filePath = Paths.get(CSV_FILE_PATH);
    /**
     * Reads the CSV file and returns a map containing user data.
     * The map's keys are usernames, and the values are maps of user attributes.
     * @return A map containing user data.
     */
    public static Map<String, Map<String, String>> readCsvFile() {
        // Initialize the map to store user data
        Map<String, Map<String, String>> userValuesMap = new HashMap<>();

        try {
            // Read the CSV file using OpenCSV library
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(filePath.toString()));
            Map<String, String> row;
            // Iterate over each row in the CSV file
            while ((row = reader.readMap()) != null) {
                String userName = row.get("user_name");

                // Create a map to store user attributes
                Map<String, String> userValues = new HashMap<>();
                // Iterate over each entry in the row and add it to the user attributes map
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    if (!entry.getKey().equals("user_name")) {
                        userValues.put(entry.getKey(), entry.getValue());
                    }
                }

                // Add the user's attributes map to the main user data map
                userValuesMap.put(userName, userValues);
            }
            reader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return userValuesMap;
    }

    /**
     * Retrieves a list of all usernames from the CSV file.
     * @return An ArrayList containing all usernames.
     */
    public static ArrayList<String> getAllUsers(){
        ArrayList<String> Users = new ArrayList<>();
        try {
            // Read the CSV file using OpenCSV library
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(filePath.toString()));
            Map<String, String> row;
            // Iterate over each row in the CSV file
            while ((row = reader.readMap()) != null) {
                // Add the username to the list
                Users.add(row.get("user_name"));
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return Users;
    }

    /**
     * Retrieves a list of usernames sorted by their high scores in descending order.
     * @return An ArrayList containing usernames sorted by high score.
     */
    public static ArrayList<String> getHighScoreOrder(){
        ArrayList<String> users = getAllUsers();
        users.sort((user1, user2) -> {
            int score1 = Integer.parseInt(getHighScore(user1));
            int score2 = Integer.parseInt(getHighScore(user2));
            // For descending order, swap user1 and user2 comparison
            return Integer.compare(score2, score1);
        });
        return users;
    }

    /**
     * Retrieves the password associated with the specified username.
     * @param userName The username to retrieve the password for.
     * @return The password associated with the username.
     */
    public static String getPassword(String userName) {
        return getField(userName, "password");
    }

    /**
     * Retrieves the number of games played by the specified user.
     * @param userName The username to retrieve the number of games played for.
     * @return The number of games played by the user.
     */
    public static String getNumGamesPlayed(String userName) {
        return getField(userName, "num_games_played");
    }

    /**
     * Retrieves the saved game status for the specified user.
     * @param userName The username to retrieve the saved game status for.
     * @return The saved game status for the user.
     */
    public static String getSavedGame(String userName) {
        return getField(userName, "saved_game?");
    }

    /**
     * Retrieves the accuracy rate for the specified user.
     * @param userName The username to retrieve the accuracy rate for.
     * @return The accuracy rate for the user.
     */
    public static String getAccuracyRate(String userName) {
        return getField(userName, "accuracy_rate");
    }

    /**
     * Retrieves the list of countries for the specified user.
     * @param userName The username to retrieve the list of countries for.
     * @return The list of countries for the user.
     */
    public static String getListOfCountry(String userName) {
        return getField(userName, "listOfCountry");
    }

    /**
     * Retrieves the high score for the specified user.
     * @param userName The username to retrieve the high score for.
     * @return The high score for the user.
     */
    public static String getHighScore(String userName) {
        return getField(userName, "highScore");
    }

    /**
     * Retrieves the value of a specific field for the specified user.
     * @param userName The username to retrieve the field value for.
     * @param fieldName The name of the field to retrieve the value for.
     * @return The value of the specified field for the user.
     */
    private static String getField(String userName, String fieldName) {
        Map<String, String> userValues = readCsvFile().get(userName);
        if (userValues == null) {
            return null;
        }
        return userValues.get(fieldName);
    }

    /**
     * Changes the password for the specified user.
     * @param userName The username to change the password for.
     * @param newPassword The new password.
     */
    public static void changePassword(String userName, String newPassword) {
        changeFieldValue(userName, "password", newPassword);
    }

    /**
     * Changes the number of games played for the specified user.
     * @param userName The username to change the number of games played for.
     * @param newNumGamesPlayed The new number of games played.
     */
    public static void changeNumGamesPlayed(String userName, String newNumGamesPlayed) {
        changeFieldValue(userName, "num_games_played", newNumGamesPlayed);
    }

    /**
     * Changes the saved game status for the specified user.
     * @param userName The username to change the saved game status for.
     * @param newSavedGame The new saved game status.
     */
    public static void changeSavedGame(String userName, String newSavedGame) {
        changeFieldValue(userName, "saved_game?", newSavedGame);
    }

    /**
     * Changes the accuracy rate for the specified user.
     * @param userName The username to change the accuracy rate for.
     * @param newAccuracyRate The new accuracy rate.
     */
    public static void changeAccuracyRate(String userName, String newAccuracyRate) {
        changeFieldValue(userName, "accuracy_rate", newAccuracyRate);
    }

    /**
     * Changes the list of countries for the specified user.
     * @param userName The username to change the list of countries for.
     * @param newListOfCountry The new list of countries.
     */
    public static void changeListOfCountry(String userName, String newListOfCountry) {
        changeFieldValue(userName, "listOfCountry", newListOfCountry);
    }

    /**
     * Changes the high score for the specified user.
     * @param userName The username to change the high score for.
     * @param newHighScore The new high score.
     */
    public static void changeHighScore(String userName, String newHighScore) {
        changeFieldValue(userName, "highScore", newHighScore);
    }

    /**
     * Changes the value of a specific field for the specified user.
     * @param userName The username to change the field value for.
     * @param fieldName The name of the field to change the value for.
     * @param newValue The new value for the field.
     */
    private static void changeFieldValue(String userName, String fieldName, String newValue) {
        try {
            // Read all lines from the CSV file
            CSVReader reader = new CSVReader(new FileReader(filePath.toString()));
            List<String[]> lines = reader.readAll();
            reader.close();


            if(!Files.exists(filePath)) {
                newFile(filePath);
            }

            // Open the CSV file in write mode
            FileWriter writer = new FileWriter(filePath.toString(), false);
            CSVWriter csvWriter = new CSVWriter(writer);

            // Iterate over each line in the CSV file
            for (String[] line : lines) {
                // If the username matches, update the specified field
                if (line[0].equals(userName)) {
                    line[getIndex(fieldName)] = newValue;
                }
                // Write the updated line to the CSV file
                csvWriter.writeNext(line);
            }
            csvWriter.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the index of a field in the CSV file's header row.
     * @param fieldName The name of the field.
     * @return The index of the field in the header row.
     */
    private static int getIndex(String fieldName) {
        String[] headers = {"password", "num_games_played", "saved_game?", "accuracy_rate", "listOfCountry", "highScore"};
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(fieldName)) {
                return i + 1; // Add 1 to account for the "user_name" field
            }
        }
        return -1; // Field not found
    }

    /**
     * Prints the values of all attributes for the specified user.
     * @param userName The username to print the values for.
     */
    public static void printUserValues(String userName) {
        Map<String, String> userValues = readCsvFile().get(userName);
        if (userValues == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("User Name: " + userName);
        for (Map.Entry<String, String> entry : userValues.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Prints the values of all attributes for all users.
     */
    public static void printAllUsers() {
        Map<String, Map<String, String>> userValuesMap = readCsvFile();

        for (Map.Entry<String, Map<String, String>> entry : userValuesMap.entrySet()) {
            String userName = entry.getKey();
            Map<String, String> userValues = entry.getValue();

            System.out.println("User Name: " + userName);
            for (Map.Entry<String, String> valueEntry : userValues.entrySet()) {
                System.out.println("  " + valueEntry.getKey() + ": " + valueEntry.getValue());
            }
            System.out.println();
        }
    }

    /**
     * Deletes the user with the specified username from the CSV file.
     * @param userName The username of the user to delete.
     */
    public static void deleteUser(String userName) {
        try {
            // Read all lines from the CSV file
            CSVReader reader = new CSVReader(new FileReader(filePath.toString()));
            List<String[]> lines = reader.readAll();
            reader.close();


            if(!Files.exists(filePath)) {
                newFile(filePath);
            }
            // Open the CSV file in write mode
            FileWriter writer = new FileWriter(filePath.toString(), false);
            CSVWriter csvWriter = new CSVWriter(writer);

            // Iterate over each line in the CSV file
            for (String[] line : lines) {
                // If the username does not match, write the line to the new CSV file
                if (!line[0].equals(userName)) {
                    csvWriter.writeNext(line);
                }
            }
            csvWriter.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a user with the specified username already exists.
     * @param userName The username to check.
     * @return true if the user exists, otherwise false.
     */
    public static boolean isDuplicateUser(String userName) {
        try {

            // Read all lines from the CSV file
            if(!Files.exists(filePath)) {
                newFile(filePath);
            }
            BufferedReader reader1 = new BufferedReader(new FileReader(filePath.toString()));
//            InputStreamReader isr = new InputStreamReader(
//                    filePath, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(reader1);
            List<String[]> lines = reader.readAll();
            reader.close();

            // Iterate over each line in the CSV file
            for (String[] line : lines) {
                // If the username matches, return true
                if (line[0].equals(userName)) {
                    return true; // User already exists
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return false; // User does not exist
    }

    /**
     * Checks if the provided username and password meet the required criteria for registration.
     * @param userName The username to check.
     * @param password The password to check.
     * @return A string indicating whether the username and password meet the criteria or the reason for failure.
     */
    public static String credentialChecker(String userName, String password) {
        if (isDuplicateUser(userName)) {
            return "User already exists.";
        }
        if ((userName.length() > 16 || userName.length() < 4) || (password.length() > 16 || password.length() < 4)) {
            System.out.println(userName.isEmpty());
            return "password and username must be between 4-16 characters";
        } else if (!userName.matches("^[a-zA-Z0-9]+$")) {
            return "password and username must only contain alphanumeric characters";
        }
        return "ok";
    }

    /**
     * Adds a new user with the specified username and password to the CSV file.
     * @param userName The username of the new user.
     * @param password The password of the new user.
     * @return A string indicating whether the user addition was successful or the reason for failure.
     */
    public static String addUser(String userName, String password) {
        if (isDuplicateUser(userName)) {
            return "User already exists.";
        }
        if((userName.length() > 16 || userName.length() < 4) || (password.length() > 16 || password.length() < 4)){
            System.out.println(userName.isEmpty());
            return "password and username must be between 4-16 characters";
        }
        else if(!userName.matches("^[a-zA-Z0-9]+$")){
            return "password and username must only contain alphanumeric characters";
        }
        else {
            try {
                // Open the CSV file in append mode to add a new entry



                if(!Files.exists(filePath)) {
                    newFile(filePath);
                }
                FileWriter writer = new FileWriter(filePath.toString(), true);

                CSVWriter csvWriter = new CSVWriter(writer);

                // Create an array for the new user's data
                String[] newUser = new String[]{"user_name", "password", "num_games_played", "saved_game?", "accuracy_rate", "listOfCountry", "highScore"};
                // Set the username, password, and default values for other attributes
                newUser[0] = userName;
                newUser[1] = password;
                newUser[2] = "0";
                newUser[3] = "N";
                newUser[4] = "100";
                newUser[5] = "None";
                newUser[6] = "0";
                // Write the new user's data to the CSV file
                csvWriter.writeNext(newUser);
                csvWriter.close();
                return "APPROVED";
            } catch (IOException e) {
                e.printStackTrace();
                return "Unknown error occurred";
            }
        }
    }

    /**
     * Creates a new file at the specified {@code filePath} with predefined CSV header and closes the writer.
     * The CSV header includes fields for user information such as username, password, number of games played,
     * saved game indicator, accuracy rate, list of countries, and high score.
     *
     * @param filePath The path where the new file will be created.
     * @throws IOException If an I/O error occurs while creating the file or writing the header.
     */
    public static void newFile(Path filePath) throws IOException {


        FileWriter writer = new FileWriter(filePath.toString(), true);

        CSVWriter csvWriter = new CSVWriter(writer);
        String[] firstLine = {"user_name","password","num_games_played","saved_game?","accuracy_rate","listOfCountry","highScore"};
        csvWriter.writeNext(firstLine);
        writer.close();

    }

    /**
     * Main method for testing purposes.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Example usage
        System.out.println("Password for jam: " + getPassword("jam"));
        System.out.println("Number of games played for jam: " + getNumGamesPlayed("jam"));
    }
}
