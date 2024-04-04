import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the {@link Country} class, assessing its ability to accurately
 * return information and graphical representations related to specific countries.
 * Tests are conducted for both valid and invalid country names to ensure
 * robustness and error handling.
 */
public class CountryTest {

    /**
     * Tests the retrieval of a country's flag. It verifies that a valid country name
     * returns a non-null {@link JLabel} containing the flag, while an invalid country
     * name results in a null response, indicating the absence of data for the given input.
     */
    @Test
    public void testGetFlag() {
        // Testing with a valid country name
        Country country = new Country("France");
        JLabel flag = country.getFlag();
        assertNotNull(flag);

        // Testing with an invalid country name
        Country invalidCountry = new Country("InvalidCountry");
        JLabel invalidFlag = invalidCountry.getFlag();
        assertNull(invalidFlag);
    }

    /**
     * Assesses the method for fetching graphical maps of countries. Ensures that a
     * valid country name yields a non-null {@link JLabel} with the map, whereas an
     * invalid country name leads to a null output, effectively handling incorrect inputs.
     */
    @Test
    public void testGetCountryMap() {
        // Valid country test case
        Country country = new Country("France");
        JLabel map = country.getCountryMap();
        assertNotNull(map);

        // Invalid country test case
        Country invalidCountry = new Country("InvalidCountry");
        JLabel invalidMap = invalidCountry.getCountryMap();
        assertNull(invalidMap);
    }

    /**
     * Verifies the functionality of retrieving a country's name. It tests that the
     * name set through the constructor is accurately returned, both for valid and
     * theoretically invalid country names, reflecting the class's data handling.
     */
    @Test
    public void testGetName() {
        // Valid name verification
        String validName = "France";
        Country country = new Country(validName);
        assertEquals(validName, country.getName());

        // Invalid name handling
        String invalidName = "InvalidCountry";
        Country invalidCountry = new Country(invalidName);
        assertEquals(invalidName, invalidCountry.getName());
    }

    /**
     * Tests the retrieval of hints or trivia related to a country. For valid country
     * names, it checks for non-null output which would typically contain relevant information.
     * For invalid country names, it also expects a non-null output, potentially an empty label
     * or a default hint, demonstrating the method's default handling for unsupported inputs.
     */
    @Test
    public void testGetHints() {
        // Valid country hints
        Country country = new Country("France");
        JLabel hints = country.getHints();
        assertNotNull(hints);

        // Handling of invalid country hints
        Country invalidCountry = new Country("InvalidCountry");
        JLabel invalidHints = invalidCountry.getHints();
        assertNotNull(invalidHints); // Expecting non-null, indicating robustness in hint retrieval
    }
}
