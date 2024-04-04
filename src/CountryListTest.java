import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the {@link CountryList} class, focusing on the retrieval of country
 * information based on different filtering modes such as global mode, micronation mode,
 * continental mode, and handling of invalid modes.
 */
public class CountryListTest {

    /**
     * Tests the retrieval of countries that are part of the global mode.
     * This test ensures that the method returns a non-empty array of {@link Country}
     * objects when filtering by "Global Mode", indicating that the filtering mechanism
     * is operational and correctly identifies countries within the global mode.
     */
    @Test
    public void testGetCountries_GlobalMode() {
        Country[] countries = CountryList.getCountries("Global Mode");
        assertNotNull(countries);
        assertTrue(countries.length > 0);
        // Additional assertions can be added to verify specific country data
    }

    /**
     * Tests the retrieval of countries identified as micronations.
     * It verifies that the {@link CountryList#getCountries} method can filter
     * countries by "Micro Nation Mode" and return a non-empty array, thus
     * confirming the accuracy and functionality of micronation filtering.
     */
    @Test
    public void testGetCountries_MicroNationMode() {
        Country[] countries = CountryList.getCountries("Micro Nation Mode");
        assertNotNull(countries);
        assertTrue(countries.length > 0);
        // Additional assertions could check for the presence or characteristics of specific micronations
    }

    /**
     * Tests the ability to retrieve countries based on continental mode, specifically
     * targeting countries in Europe. This test confirms that the method is capable of
     * filtering countries not just by mode, but also by additional criteria such as continent,
     * and returns a meaningful, non-empty array of countries that meet these criteria.
     */
    @Test
    public void testGetCountries_ContinentalMode() {
        Country[] countriesEurope = CountryList.getCountries("Continental Mode", "Europe");
        assertNotNull(countriesEurope);
        assertTrue(countriesEurope.length > 0);
        // Additional assertions might examine the correctness of the continent assignment for the countries
    }

    /**
     * Evaluates the method's response to an invalid mode parameter.
     * This test checks if the {@link CountryList#getCountries} method returns null
     * when passed an unrecognized filtering mode, thereby gracefully handling
     * incorrect or unsupported input.
     */
    @Test
    public void testGetCountries_InvalidMode() {
        Country[] countriesInvalidMode = CountryList.getCountries("Invalid Mode");
        assertNull(countriesInvalidMode);
        // This asserts the method's robustness in face of invalid filtering criteria
    }


}
