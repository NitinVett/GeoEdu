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
public class CountryDatabase {

    private static final String CSV_FILE_PATH = "Country Data/geocraftv2country.csv";

    public static Map<String, Map<String, String>> readCsvFile() {
        Map<String, Map<String, String>> userValuesMap = new HashMap<>();

        try {
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(CSV_FILE_PATH));
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                String ID = row.get("Country Name");


                Map<String, String> userValues = new HashMap<>();
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    if (!entry.getKey().equals("Country Name")) {
                        userValues.put(entry.getKey(), entry.getValue());
                    }
                }

                userValuesMap.put(ID, userValues);
            }
            reader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return userValuesMap;
    }

    public static ArrayList<String> getAllUsers() {
        ArrayList<String> Users = new ArrayList<>();
        try {
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(CSV_FILE_PATH));
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                Users.add(row.get("Country Name"));

            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return Users;
    }


    public static String getCountryID(String name) {

        return getField(name, "Country Name");
    }

    public static String getContinentMode(String name) {
        return getField(name, "Continent Mode");
    }

    public static String getContinent(String name) {
        return getField(name, "Continent Name");
    }

    public static String getGlobalMode(String name) {
        return getField(name, "Global Mode");
    }

    public static String getMicronationMode(String name) {
        return getField(name, "Micro Nation Mode");
    }

    public static String hints(String name) {
        return getField(name, "Hints");
    }

    private static String getField(String name, String fieldName) {
        Map<String, String> userValues = readCsvFile().get(name);
        if (userValues == null) {
            return null;
        }
        return userValues.get(fieldName);
    }

    private static int getIndex(String fieldName) {
        String[] headers = {"Country Name", "ID", "Continent Mode", "Continent Name", "Global Mode", "Micro Nation Mode", "Hints"};
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(fieldName)) {
                return i + 1; // Add 1 to account for the "user_name" field
            }
        }
        return -1; // Field not found
    }


    public static void printCountryInfo(String name) {
        Map<String, String> userValues = readCsvFile().get(name);
        if (userValues == null) {
            System.out.println("Country not found");
            return;
        }

        System.out.println("Country Name " + name);
        for (Map.Entry<String, String> entry : userValues.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void printAllCountries() {
        Map<String, Map<String, String>> userValuesMap = readCsvFile();
        int counter = 0;
        for (Map.Entry<String, Map<String, String>> entry : userValuesMap.entrySet()) {
            String name = entry.getKey();
            Map<String, String> userValues = entry.getValue();

            System.out.println("Country name " + name);
            for (Map.Entry<String, String> valueEntry : userValues.entrySet()) {
                System.out.println("  " + valueEntry.getKey() + ": " + valueEntry.getValue());
            }
            counter++;
            System.out.println();
        }
        System.out.println(counter);
    }

    public static Map<String, Map<String, String>> getCountriesWithColumnYes(String columnName) {
        Map<String, Map<String, String>> allCountries = readCsvFile();
        Map<String, Map<String, String>> filteredCountries = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : allCountries.entrySet()) {
            String countryName = entry.getKey();
            Map<String, String> countryData = entry.getValue();

            String columnValue = countryData.get(columnName);
            if (columnValue != null && ("Yes".equals(columnValue) || "yes".equals(columnValue))) {
                filteredCountries.put(countryName, countryData);
            }
        }

        return filteredCountries;
    }

    public static Map<String, Map<String, String>> getCountriesWithContinentModeAndContinent(String continent) {
        Map<String, Map<String, String>> allCountries = readCsvFile();
        Map<String, Map<String, String>> filteredCountries = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : allCountries.entrySet()) {
            String countryName = entry.getKey();
            Map<String, String> countryData = entry.getValue();

            String continentMode = countryData.get("Continent Mode");
            String countryContinent = countryData.get("Continent Name");
//            System.out.println(countryContinent);
            if ("Yes".equalsIgnoreCase(continentMode) && continent.equalsIgnoreCase(countryContinent)) {
//                System.out.println("Hi");
                filteredCountries.put(countryName, countryData);
            }
        }

        return filteredCountries;
    }


    public static void main(String[] args) {
        // Specify the continent you want to filter by
        String targetContinent = "Asia"; // Change this to the continent you want to filter by

        // Call the method to get the filtered countries
        Map<String, Map<String, String>> filteredCountries = getCountriesWithContinentModeAndContinent(targetContinent);
            int count =0;
        // Print the filtered countries
        System.out.println("Filtered Countries:");
        for (Map.Entry<String, Map<String, String>> entry : filteredCountries.entrySet()) {
            String countryName = entry.getKey();
            Map<String, String> countryData = entry.getValue();
            System.out.println(countryName + ": " + countryData);
            count++;
        }
        System.out.println(count);
    }
//        Map<String, Map<String, String>> countriesWithGlobalModeYes = getCountriesWithColumnYes("Micro Nation Mode");
//        //System.out.println("Countries with Global Mode Yes:");
//        int count = 0;
//        for (Map.Entry<String, Map<String, String>> entry : countriesWithGlobalModeYes.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//            count++;
//        }
//        System.out.println(count);
//    }
    //printAllUsers();
//        System.out.println("Password for jam: " + getPassword("jam"));
//        System.out.println("Number of games played for jam: " + getNumGamesPlayed("jam"));
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
    //       printAllCountries();
    //printCountryInfo("China");
//        addUser("blahsssh");
}

