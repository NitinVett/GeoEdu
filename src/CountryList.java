import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * CountryList class provides methods to retrieve a list of country objects based on different modes.
 */
public class CountryList {

    /**
     * Retrieves a list of countries based on the specified mode.
     * If the mode is "Global Mode", it returns all countries.
     * If the mode is "Micro Nation Mode", it returns all micro nations.
     * If the mode is "Continental Mode", it returns countries within the specified continent.
     *
     * @param mode The mode for retrieving countries ("Global Mode", "Micro Nation Mode", or "Continental Mode").
     * @return An array of Country objects.
     */
    public static Country[] getCountries(String mode) {
        return getCountries(mode, null);
    }

    /**
     * Retrieves a list of countries based on the specified mode and continent.
     *
     * @param mode      The mode for retrieving countries ("Global Mode" or "Micro Nation Mode").
     * @param continent The continent for retrieving countries (applicable only if mode is "Continental Mode").
     * @return An array of Country objects.
     */
    public static Country[] getCountries(String mode, String continent) {
        if (Objects.equals(mode, "Global Mode")) {
            return getGlobalOrMicroCountries("Global Mode");
        } else if (Objects.equals(mode, "Micro Nation Mode")) {
            return getGlobalOrMicroCountries("Micro Nation Mode");
        } else if (Objects.equals(mode, "Continental Mode")) {
            return getContinentCountries(continent);
        } else {
            // Handle invalid mode
            return null;
        }
    }

    /**
     * Retrieves countries for "Global Mode" or "Micro Nation Mode".
     *
     * @param mode The mode ("Global Mode" or "Micro Nation Mode").
     * @return An array of Country objects.
     */
    private static Country[] getGlobalOrMicroCountries(String mode) {
        Map<String, Map<String, String>> countriesWithGlobalModeYes = CountryDatabase.getCountriesWithColumnYes(mode);
        return getCountriesFromArray(countriesWithGlobalModeYes);
    }

    /**
     * Retrieves countries for "Continental Mode".
     *
     * @param continent The continent for retrieving countries.
     * @return An array of Country objects.
     */
    private static Country[] getContinentCountries(String continent) {
        Map<String, Map<String, String>> countriesW = CountryDatabase.getCountriesWithContinentModeAndContinent(continent);
        return getCountriesFromArray(countriesW);
    }

    /**
     * Converts a map of countries to an array of Country objects.
     *
     * @param countriesWithGlobalModeYes The map containing countries.
     * @return An array of Country objects.
     */
    private static Country[] getCountriesFromArray(Map<String, Map<String, String>> countriesWithGlobalModeYes) {
        int size = countriesWithGlobalModeYes.size();
        int index = 0;
        Country[] array = new Country[size];
        for (Map.Entry<String, Map<String, String>> entry : countriesWithGlobalModeYes.entrySet()) {
            String countryName = entry.getKey();
            Country country = new Country(countryName); // Assuming Country constructor takes country name as argument
            array[index++] = country;
        }
        return array;
    }
}
