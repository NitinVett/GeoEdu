import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CountryDatabase} class. This suite covers functionality
 * related to reading from CSV files, retrieving various data about countries,
 * and ensuring the database operations return expected results.
 */
public class CountryDatabaseTest {

    /**
     * Tests whether the CSV file reading functionality correctly produces a non-empty
     * map of country data, ensuring that the reading process works and the data structure
     * is populated as expected.
     */
    @Test
    public void testReadCsvFile() {
        Map<String, Map<String, String>> countryData = CountryDatabase.readCsvFile();
        assertNotNull(countryData);
        assertFalse(countryData.isEmpty());
    }

    /**
     * Validates that the method for retrieving all users indeed returns a non-empty list,
     * indicating that the database contains entries and the retrieval mechanism is functional.
     *
     * @throws CsvValidationException if the CSV parsing encounters an issue.
     */
    @Test
    public void testGetAllUsers() throws CsvValidationException {
        List<String> allUsers = CountryDatabase.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    /**
     * Ensures that the method for fetching a country's ID by its name returns the correct ID,
     * using "India" as a test case and expecting a specific, known ID as the result.
     */
    @Test
    public void testGetCountryID() {
        String countryName = "India";
        String expectedCountryID = "4";
        String actualCountryID = CountryDatabase.getCountryID(countryName);
        assertEquals(expectedCountryID, actualCountryID);
    }

    /**
     * Tests if the continent mode retrieval by country name is accurate,
     * specifically checking if "India" is correctly associated with its continent mode.
     */
    @Test
    public void testGetContinentMode() {
        String continentMode = CountryDatabase.getContinentMode("India");
        assertNotNull(continentMode);
        assertEquals("Yes", continentMode);
    }

    /**
     * Verifies that the method to get a country's continent returns the correct continent,
     * with "India" expected to be in "Asia".
     */
    @Test
    public void testGetContinent() {
        String continent = CountryDatabase.getContinent("India");
        assertNotNull(continent);
        assertEquals("Asia", continent);
    }

    /**
     * Confirms the functionality of retrieving a country's global mode status.
     * The test checks if the "India" entry correctly indicates its global mode status.
     */
    @Test
    public void testGetGlobalMode() {
        String globalMode = CountryDatabase.getGlobalMode("India");
        assertNotNull(globalMode);
        assertEquals("Yes", globalMode);
    }

    /**
     * Tests the accuracy of the method determining if a country is considered a micronation.
     * Specifically, it verifies that "India" is correctly not identified as a micronation.
     */
    @Test
    public void testGetMicronationMode() {
        String micronationMode = CountryDatabase.getMicronationMode("India");
        assertNotNull(micronationMode);
        assertEquals("No", micronationMode);
    }

    /**
     * Assesses the ability of the database to provide hints or trivia related to a specific country.
     * This test ensures that the method returns meaningful data for "India", such as notable landmarks.
     */
    @Test
    public void testHints() {
        String hints = CountryDatabase.hints("India");
        assertNotNull(hints);
        assertTrue(hints.contains("The Taj Mahal in Agra is a monument of enduring love."));
    }

    /**
     * Evaluates the method's capability to retrieve a specific field's value for a given country.
     * It checks if the continent mode for "India" is correctly fetched and matches the expected value.
     */
    @Test
    public void testGetField() {
        String countryName = "India";
        String expectedContinentMode = "Yes";
        String actualContinentMode = CountryDatabase.getContinentMode(countryName);
        assertEquals(expectedContinentMode, actualContinentMode);
    }

    /**
     * Verifies that the database correctly identifies the index of a given column name.
     * This test confirms that the index for "Country Name" is as expected, demonstrating
     * the method's ability to navigate the database schema.
     */
    @Test
    public void testGetIndex() {
        int index = CountryDatabase.getIndex("Country Name");
        assertEquals(1, index);
    }

    /**
     * Tests the retrieval of countries with a specific column set to "Yes",
     * focusing on the "Global Mode" attribute. The test confirms that the
     * method successfully identifies countries meeting this criterion and
     * that the returned structure is not empty, indicating correct functionality.
     */
    @Test
    public void testGetCountriesWithColumnYes() {
        Map<String, Map<String, String>> countriesWithColumnYes = CountryDatabase.getCountriesWithColumnYes("Global Mode");
        assertNotNull(countriesWithColumnYes);
        assertFalse(countriesWithColumnYes.isEmpty());
    }

    /**
     * Validates the method's ability to fetch countries from a specified continent
     * with their continent mode set to "Yes". Using "Asia" as a test case, it checks
     * whether the returned data accurately reflects the database entries.
     */
    @Test
    public void testGetCountriesWithContinentModeAndContinent() {
        Map<String, Map<String, String>> countries = CountryDatabase.getCountriesWithContinentModeAndContinent("Asia");
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
    }
}
