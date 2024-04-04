import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountryListTest {

    @Test
    public void testGetCountries_GlobalMode() {
        Country[] countries = CountryList.getCountries("Global Mode");
        assertNotNull(countries);
        assertTrue(countries.length > 0);
        // Add more assertions as needed
    }

    @Test
    public void testGetCountries_MicroNationMode() {
        Country[] countries = CountryList.getCountries("Micro Nation Mode");
        assertNotNull(countries);
        assertTrue(countries.length > 0);
        // Add more assertions as needed
    }

    @Test
    public void testGetCountries_ContinentalMode() {
        // Test with a specific continent
        Country[] countriesEurope = CountryList.getCountries("Continental Mode", "Europe");
        assertNotNull(countriesEurope);
        assertTrue(countriesEurope.length > 0);
        // Add more assertions as needed
    }

    @Test
    public void testGetCountries_InvalidMode() {
        // Test with an invalid mode
        Country[] countriesInvalidMode = CountryList.getCountries("Invalid Mode");
        assertNull(countriesInvalidMode);
    }

    // Add more test cases for other scenarios as needed
}
