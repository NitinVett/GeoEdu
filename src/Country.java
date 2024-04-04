import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Country class represents a country entity with its name, flag, hints, and country map.
 */
public class Country {
    /**
     * JLabel for flag of countries
     */
    private JLabel flag;
    /**
     * name of countries
     */
    private String name;
    /**
     * JLabel for hints of countries
     */
    private JLabel hints;
    /**
     * JLabel for map of countries
     */
    private JLabel countryMap;
    /**
     * integer ID of countries
     */
    private int ID;

    /**
     * Constructs a Country object with the specified name.
     *
     * @param name The name of the country.
     */
    public Country(String name) {
        this.name = name;
    }

    /**
     * Retrieves the flag image for the country.
     *
     * @return The JLabel containing the flag image.
     */
    public JLabel getFlag() {
        BufferedImage flagImage = null;
        try {

            String imagePath = "Flags/" + this.getName() + ".png";
            flagImage = ImageIO.read(getClass().getResourceAsStream(imagePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (flagImage != null) {
            Image resizedImage = flagImage.getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            this.flag = new JLabel(new ImageIcon(resizedImage));
        }
        return this.flag;
    }

    /**
     * Retrieves the country map image.
     *
     * @return The JLabel containing the country map image.
     */
    public JLabel getCountryMap() {
        BufferedImage mapImage = null;
        try {
            String imagePath = "Maps/" + this.getName() + ".png";
            mapImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mapImage != null) {
            Image resizedImage = mapImage.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
            countryMap = new JLabel(new ImageIcon(resizedImage));
        }
        return this.countryMap;
    }

    /**
     * Retrieves the name of the country.
     *
     * @return The name of the country.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves hints about the country.
     *
     * @return The JLabel containing hints about the country.
     */
    public JLabel getHints() {
        JLabel hintsLabel = new JLabel();
        String hintString = CountryDatabase.hints(name);
        System.out.println(hintString);
        if(Objects.isNull(hintString)){
            return hintsLabel;
        }
        try {

            String[] lines = hintString.split("\n");
            StringBuilder content = new StringBuilder();
            int lineCount = 0;
            for (String line : lines) {
                if (lineCount >= 3) break; // Read only 3 lines
                content.append(line).append("<br>");
                lineCount++;
            }
            hintsLabel.setText("<html>" + content.toString() + "</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hintsLabel;
    }
}
