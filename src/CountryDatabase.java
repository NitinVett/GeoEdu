import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Reads the CSV file specified by the {@code CSV_FILE_PATH} constant and retrieves the "Country Name" from each row.
 *
 * with the CSV structure that prevent it from being read correctly.
 * IOException If an I/O error occurs when opening or reading the file.
 * An {@link ArrayList} containing the "Country Name" from each row of the CSV file.
 */
public class CountryDatabase {

    /**
     * path of country data csv file
     */
    private static final String CSV_FILE_PATH = "geocraftv2country.csv";
    static Path filePath = Paths.get(CSV_FILE_PATH);
    /**
     * Reads the CSV file and returns a map containing country data.
     * @return An {@link ArrayList} containing the "Country Name" from each row of the CSV file.
     */
    public static Map<String, Map<String, String>> readCsvFile() {
        Map<String, Map<String, String>> countryDataMap = new HashMap<>();

        try {
            InputStream inputStream = CountryDatabase.class.getClassLoader().getResourceAsStream("geocraftv2country.csv");
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream));

            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(reader1);
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

            InputStream inputStream = CountryDatabase.class.getClassLoader().getResourceAsStream("geocraftv2country.csv");
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream));

            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(reader1);
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
    /**
     * Reads a CSV file specified by the {@code CSV_FILE_PATH} constant and extracts the "Country Name" from each row.
     * The method assumes that the CSV file has a header row and that one of the columns is named "Country Name".
     *
     * @return An {@link ArrayList} containing the "Country Name" from each row of the CSV file. If an error occurs
     *         during reading the file, the stack trace is printed, and an empty list is returned.
     *
     * @throws CsvValidationException If the CSV content does not match the expected format or if there are issues
     *                                with the CSV structure that prevent it from being read correctly.
     */
    public static ArrayList<String> getAllUsers() throws CsvValidationException{
        ArrayList<String> Users = new ArrayList<>();
        try {
            InputStream inputStream = CountryDatabase.class.getClassLoader().getResourceAsStream("geocraftv2country.csv");
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream));

            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(reader1);
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                Users.add(row.get("Country Name"));

            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return Users;
    }
    /**
     * Retrieves the index of a specified field name from a predefined list of headers. This method is useful for
     * identifying the position of a field within a CSV file or similar structured data, given that the headers
     * represent the fields in the data structure.
     *
     * The headers are as follows: "Country Name", "ID", "Continent Mode", "Continent Name", "Global Mode",
     * "Micro Nation Mode", and "Hints".
     *
     * @param fieldName The name of the field for which the index is to be retrieved. This is case-sensitive.
     * @return The 1-based index of the field within the predefined headers. If the field is found, its index
     *         (adjusted by 1) is returned. If the field is not found, -1 is returned to indicate the absence
     *         of the field within the headers.
     */
    public static int getIndex(String fieldName) {
        String[] headers = {"Country Name", "ID", "Continent Mode", "Continent Name", "Global Mode", "Micro Nation Mode", "Hints"};
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(fieldName)) {
                return i + 1; // Add 1 to account for the "user_name" field
            }
        }
        return -1; // Field not found
    }
}
