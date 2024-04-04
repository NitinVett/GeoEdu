import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class CountryTest {

    @Test
    public void testGetFlag() {
        // Test with a valid country name
        Country country = new Country("France");
        JLabel flag = country.getFlag();
        assertNotNull(flag);
        // Test with an invalid country name
        Country invalidCountry = new Country("InvalidCountry");
        JLabel invalidFlag = invalidCountry.getFlag();
        assertNull(invalidFlag);
    }

    @Test
    public void testGetCountryMap() {
        // Test with a valid country name
        Country country = new Country("France");
        JLabel map = country.getCountryMap();
        assertNotNull(map);
        // Test with an invalid country name
        Country invalidCountry = new Country("InvalidCountry");
        JLabel invalidMap = invalidCountry.getCountryMap();
        assertNull(invalidMap);
    }

    @Test
    public void testGetName() {
        // Test with a valid country name
        String validName = "France";
        Country country = new Country(validName);
        assertEquals(validName, country.getName());
        // Test with an invalid country name
        String invalidName = "InvalidCountry";
        Country invalidCountry = new Country(invalidName);
        assertEquals(invalidName, invalidCountry.getName());
    }

    @Test
    public void testGetHints() {
        // Test with a valid country name
        Country country = new Country("France");
        JLabel hints = country.getHints();
        assertNotNull(hints);
        // Test with an invalid country name
        Country invalidCountry = new Country("InvalidCountry");
        JLabel invalidHints = invalidCountry.getHints();
        assertNotNull(invalidHints); // Expecting to get an empty label or default hint
    }
}
