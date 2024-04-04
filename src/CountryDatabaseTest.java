import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CountryDatabaseTest {

    @Test
    public void testReadCsvFile() {
        Map<String, Map<String, String>> countryData = CountryDatabase.readCsvFile();
        assertNotNull(countryData);
        assertFalse(countryData.isEmpty());
    }

    @Test
    public void testGetAllUsers() {
        List<String> allUsers = CountryDatabase.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    public void testGetCountryID() {
        String countryName = "India";
        String expectedCountryID = "4";
        String actualCountryID = CountryDatabase.getCountryID(countryName);
        assertEquals(expectedCountryID, actualCountryID);
    }

    @Test
    public void testGetContinentMode() {
        String continentMode = CountryDatabase.getContinentMode("India");
        assertNotNull(continentMode);
        assertEquals("Yes", continentMode);
    }

    @Test
    public void testGetContinent() {
        String continent = CountryDatabase.getContinent("India");
        assertNotNull(continent);
        assertEquals("Asia", continent);
    }

    @Test
    public void testGetGlobalMode() {
        String globalMode = CountryDatabase.getGlobalMode("India");
        assertNotNull(globalMode);
        assertEquals("Yes", globalMode);
    }

    @Test
    public void testGetMicronationMode() {
        String micronationMode = CountryDatabase.getMicronationMode("India");
        assertNotNull(micronationMode);
        assertEquals("No", micronationMode);
    }

    @Test
    public void testHints() {
        String hints = CountryDatabase.hints("India");
        assertNotNull(hints);
        assertTrue(hints.contains("The Taj Mahal in Agra is a monument of enduring love."));
    }

    @Test
    public void testGetField() {
        String countryName = "India";
        String expectedContinentMode = "Yes";
        String actualContinentMode = CountryDatabase.getContinentMode(countryName);
        assertEquals(expectedContinentMode, actualContinentMode);
    }

    @Test
    public void testGetIndex() {
        int index = CountryDatabase.getIndex("Country Name");
        assertEquals(1, index);
    }

    @Test
    public void testGetCountriesWithColumnYes() {
        Map<String, Map<String, String>> countriesWithColumnYes = CountryDatabase.getCountriesWithColumnYes("Global Mode");
        assertNotNull(countriesWithColumnYes);
        assertFalse(countriesWithColumnYes.isEmpty());
    }

    @Test
    public void testGetCountriesWithContinentModeAndContinent() {
        Map<String, Map<String, String>> countries = CountryDatabase.getCountriesWithContinentModeAndContinent("Asia");
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
    }
}
