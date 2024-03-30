import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

// Returns a list of country object specified by the mode
// can recieve one or two parameters
public class CountryList {
    public static Country[] getCountries(String mode) {
        return getCountries(mode, null);
    }

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

    private static Country[] getGlobalOrMicroCountries(String mode) {
        Map<String, Map<String, String>> countriesWithGlobalModeYes = CountryDatabase.getCountriesWithColumnYes(mode);
        return getCountriesFromArray(countriesWithGlobalModeYes);
    }

    private static Country[] getContinentCountries(String continent) {
        Map<String, Map<String, String>> countriesW = CountryDatabase.getCountriesWithContinentModeAndContinent(continent);
        return getCountriesFromArray(countriesW);
    }

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

    // Example of main method to test the utility function
    public static void main(String[] args) {
        Country[] testGlobal = CountryList.getCountries("Global Mode", null);
        Country[] testContinent = CountryList.getCountries("Continental Mode", "Europe");
        System.out.println(Arrays.toString(testGlobal));
        System.out.println(Arrays.toString(testContinent));
    }
}
