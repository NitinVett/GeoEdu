import java.io.*;
import java.util.*;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * CountryDatabase class provides methods to interact with a CSV file containing country data.
 * It handles reading, retrieving, and filtering country information.
 */
public class CountryDatabase {

    private static final String CSV_FILE_PATH = "Country Data/geocraftv2country.csv";

    /**
     * Reads the CSV file and returns a map containing country data.
     *
     * @return A map containing country data.
     */
    public static Map<String, Map<String, String>> readCsvFile() {
        Map<String, Map<String, String>> countryDataMap = new HashMap<>();

        try {
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(CSV_FILE_PATH));
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                String countryName = row.get("Country Name");

                Map<String, String> countryValues = new HashMap<>();
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    if (!entry.getKey().equals("Country Name")) {
                        countryValues.put(entry.getKey(), entry.getValue());
                    }
                }

                countryDataMap.put(countryName, countryValues);
            }
            reader.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return countryDataMap;
    }

    /**
     * Retrieves the IDs of all countries.
     *
     * @return An ArrayList containing the IDs of all countries.
     */
    public static ArrayList<String> getAllCountryIDs() {
        ArrayList<String> countryIDs = new ArrayList<>();
        try {
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(CSV_FILE_PATH));
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                countryIDs.add(row.get("Country Name"));
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return countryIDs;
    }

    /**
     * Retrieves the value of the specified field for the given country.
     *
     * @param name      The name of the country.
     * @param fieldName The name of the field.
     * @return The value of the specified field for the given country.
     */
    public static String getField(String name, String fieldName) {
        Map<String, String> countryValues = readCsvFile().get(name);
        if (countryValues == null) {
            return null;
        }
        return countryValues.get(fieldName);
    }

    /**
     * Retrieves the country ID for the given country name.
     *
     * @param name The name of the country.
     * @return The country ID.
     */
    public static String getCountryID(String name) {
        return getField(name, "Country Name");
    }

    /**
     * Retrieves the continent mode for the given country name.
     *
     * @param name The name of the country.
     * @return The continent mode.
     */
    public static String getContinentMode(String name) {
        return getField(name, "Continent Mode");
    }

    /**
     * Retrieves the continent for the given country name.
     *
     * @param name The name of the country.
     * @return The continent.
     */
    public static String getContinent(String name) {
        return getField(name, "Continent Name");
    }

    /**
     * Retrieves the global mode for the given country name.
     *
     * @param name The name of the country.
     * @return The global mode.
     */
    public static String getGlobalMode(String name) {
        return getField(name, "Global Mode");
    }

    /**
     * Retrieves the micronation mode for the given country name.
     *
     * @param name The name of the country.
     * @return The micronation mode.
     */
    public static String getMicronationMode(String name) {
        return getField(name, "Micro Nation Mode");
    }

    /**
     * Retrieves the hints for the given country name.
     *
     * @param name The name of the country.
     * @return The hints.
     */
    public static String hints(String name) {
        return getField(name, "Hints");
    }

    /**
     * Prints information about the specified country.
     *
     * @param name The name of the country.
     */
    public static void printCountryInfo(String name) {
        Map<String, String> countryValues = readCsvFile().get(name);
        if (countryValues == null) {
            System.out.println("Country not found");
            return;
        }

        System.out.println("Country Name " + name);
        for (Map.Entry<String, String> entry : countryValues.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Prints information about all countries.
     */
    public static void printAllCountries() {
        Map<String, Map<String, String>> countryDataMap = readCsvFile();
        int counter = 0;
        for (Map.Entry<String, Map<String, String>> entry : countryDataMap.entrySet()) {
            String name = entry.getKey();
            Map<String, String> countryValues = entry.getValue();

            System.out.println("Country name " + name);
            for (Map.Entry<String, String> valueEntry : countryValues.entrySet()) {
                System.out.println("  " + valueEntry.getKey() + ": " + valueEntry.getValue());
            }
            counter++;
            System.out.println();
        }
        System.out.println(counter);
    }

    /**
     * Retrieves countries with the specified column value set to "Yes".
     *
     * @param columnName The name of the column.
     * @return A map containing countries with the specified column value set to "Yes".
     */
    public static Map<String, Map<String, String>> getCountriesWithColumnYes(String columnName) {
        Map<String, Map<String, String>> allCountries = readCsvFile();
        Map<String, Map<String, String>> filteredCountries = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : allCountries.entrySet()) {
            String countryName = entry.getKey();
            Map<String, String> countryData = entry.getValue();

            String columnValue = countryData.get(columnName);
            if (columnValue != null && ("Yes".equalsIgnoreCase(columnValue))) {
                filteredCountries.put(countryName, countryData);
            }
        }

        return filteredCountries;
    }

    /**
     * Retrieves countries with the specified continent mode and continent name.
     *
     * @param continent The name of the continent.
     * @return A map containing countries with the specified continent mode and continent name.
     */
    public static Map<String, Map<String, String>> getCountriesWithContinentModeAndContinent(String continent) {
        Map<String, Map<String, String>> allCountries = readCsvFile();
        Map<String, Map<String, String>> filteredCountries = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : allCountries.entrySet()) {
            String countryName = entry.getKey();
            Map<String, String> countryData = entry.getValue();

            String continentMode = countryData.get("Continent Mode");
            String countryContinent = countryData.get("Continent Name");

            if ("Yes".equalsIgnoreCase(continentMode) && continent.equalsIgnoreCase(countryContinent)) {
                filteredCountries.put(countryName, countryData);
            }
        }

        return filteredCountries;
    }
}
