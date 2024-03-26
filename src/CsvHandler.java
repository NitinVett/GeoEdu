import java.io.*;
import java.util.*;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Can use this class to make logic, login, register user, update highscore, get a list of the country IDs remaining in
 * saved game, can add more classes to this.
 * Database handler class
 * Handles everything
 * Every function possible to modify the csv file.
 */
public class CsvHandler {

    private static final String CSV_FILE_PATH = "Database/database.csv";

    public static Map<String, Map<String, String>> readCsvFile() {
        Map<String, Map<String, String>> userValuesMap = new HashMap<>();

        try {
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(CSV_FILE_PATH));
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                String userName = row.get("user_name");

                Map<String, String> userValues = new HashMap<>();
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    if (!entry.getKey().equals("user_name")) {
                        userValues.put(entry.getKey(), entry.getValue());
                    }
                }

                userValuesMap.put(userName, userValues);
            }
            reader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return userValuesMap;
    }

    public static String getPassword(String userName) {
        return getField(userName, "password");
    }

    public static String getNumGamesPlayed(String userName) {
        return getField(userName, "num_games_played");
    }

    public static String getSavedGame(String userName) {
        return getField(userName, "saved_game?");
    }

    public static String getAccuracyRate(String userName) {
        return getField(userName, "accuracy_rate");
    }

    public static String getListOfCountry(String userName) {
        return getField(userName, "listOfCountry");
    }

    public static String getHighScore(String userName) {
        return getField(userName, "highScore");
    }

    private static String getField(String userName, String fieldName) {
        Map<String, String> userValues = readCsvFile().get(userName);
        if (userValues == null) {
            return null;
        }
        return userValues.get(fieldName);
    }

    public static void changePassword(String userName, String newPassword) {
        changeFieldValue(userName, "password", newPassword);
    }

    public static void changeNumGamesPlayed(String userName, String newNumGamesPlayed) {
        changeFieldValue(userName, "num_games_played", newNumGamesPlayed);
    }

    public static void changeSavedGame(String userName, String newSavedGame) {
        changeFieldValue(userName, "saved_game?", newSavedGame);
    }

    public static void changeAccuracyRate(String userName, String newAccuracyRate) {
        changeFieldValue(userName, "accuracy_rate", newAccuracyRate);
    }

    public static void changeListOfCountry(String userName, String newListOfCountry) {
        changeFieldValue(userName, "listOfCountry", newListOfCountry);
    }

    public static void changeHighScore(String userName, String newHighScore) {
        changeFieldValue(userName, "highScore", newHighScore);
    }

    private static void changeFieldValue(String userName, String fieldName, String newValue) {
        try {
            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            List<String[]> lines = reader.readAll();
            reader.close();

            FileWriter writer = new FileWriter(CSV_FILE_PATH, false); // Open in append mode to overwrite existing content
            CSVWriter csvWriter = new CSVWriter(writer);

            for (String[] line : lines) {
                if (line[0].equals(userName)) {
                    line[getIndex(fieldName)] = newValue;
                }
                csvWriter.writeNext(line);
            }
            csvWriter.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private static int getIndex(String fieldName) {
        String[] headers = {"password", "num_games_played", "saved_game?", "accuracy_rate", "listOfCountry", "highScore"};
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(fieldName)) {
                return i + 1; // Add 1 to account for the "user_name" field
            }
        }
        return -1; // Field not found
    }


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
    public static void deleteUser(String userName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            List<String[]> lines = reader.readAll();
            reader.close();

            FileWriter writer = new FileWriter(CSV_FILE_PATH, false); // Open in append mode to overwrite existing content
            CSVWriter csvWriter = new CSVWriter(writer);

            for (String[] line : lines) {
                if (!line[0].equals(userName)) {
                    csvWriter.writeNext(line);
                }
            }
            csvWriter.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    public static boolean isDuplicateUser(String userName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
            List<String[]> lines = reader.readAll();
            reader.close();

            for (String[] line : lines) {
                if (line[0].equals(userName)) {
                    return true; // User already exists
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return false; // User does not exist
    }

    public static void addUser(String userName) {
        if (isDuplicateUser(userName)) {
            System.out.println("Error: User already exists.");
            return;
        }

        try {
            FileWriter writer = new FileWriter(CSV_FILE_PATH, true); // Append mode to add new entry
            CSVWriter csvWriter = new CSVWriter(writer);

            String[] newUser = new String[]{"user_name", "password", "num_games_played", "saved_game?", "accuracy_rate", "listOfCountry", "highScore"};
            newUser[0] = userName; // Set the username
            for (int i = 1; i < newUser.length; i++) {
                newUser[i] = ""; // Set other fields to default value
            }

            csvWriter.writeNext(newUser);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
          printAllUsers();
//        System.out.println("Password for ahafeez7: " + getPassword("ahafeez7"));
//        System.out.println("Number of games played for ahafeez7: " + getNumGamesPlayed("ahafeez7"));
//        System.out.println("Saved game for ahafeez7: " + getSavedGame("ahafeez7"));
//        System.out.println("Accuracy rate for ahafeez7: " + getAccuracyRate("ahafeez7"));
//        System.out.println("List of countries for ahafeez7: " + getListOfCountry("ahafeez7"));
//        System.out.println("High score for ahafeez7: " + getHighScore("ahafeez7"));
//        changeHighScore("ahafeez7", "4500");
//        changeSavedGame("ahafeez7", "Y");
//        System.out.println("High score for ahafeez7: " + getHighScore("ahafeez7"));
//        System.out.println("Saved game for ahafeez7: " + getSavedGame("ahafeez7"));
        // Example usage of changing field value
//        changePassword("ahafeez7", "newPassword");
//        changeNumGamesPlayed("ahafeez7", "7");
//        changeSavedGame("ahafeez7", "Y");
//        changeAccuracyRate("ahafeez7", "60%");
//        changeListOfCountry("ahafeez7", "[US]");
//        changeHighScore("ahafeez7", "110");
//        deleteUser("nitin");
//        printAllUsers();
//        addUser("blahsssh");
    }
}
