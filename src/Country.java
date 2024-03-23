//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

// Rough idea in my head: text file contains names for different countries, go through line by line and read off and
// initialise an object

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Country {
    private JLabel flag;
    private String name;
    private JLabel hints;
    private JLabel countryMap;

    public Country(String nameOfCountry) {
        this.name = nameOfCountry;
    }

    public JLabel getFlag() {
        return this.flag;
    }

    public JLabel getCountryMap() {

        BufferedImage backgroundImage = null;
        try {
            String folderPath = "Country Data/Maps";
            String imagePath = folderPath + "/" + this.getName() + ".png";
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (backgroundImage != null) {
            countryMap = new JLabel(new ImageIcon(backgroundImage));
            countryMap.setBounds(0, 0, screenSize.width, screenSize.height);
        }
        return this.countryMap;
    }

    public String getName() {
        return this.name;
    }

    public JLabel getHints() {
        JLabel hints = new JLabel();

        try {
            String filePath = "Country Data/Hints/" + this.getName() + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder content = new StringBuilder();
            int lineCount = 0;
            while ((line = reader.readLine()) != null && lineCount < 3) { // Read only 3 lines
                content.append(line).append("<br>");
                lineCount++;
            }
            reader.close();
            hints.setText("<html>" + content.toString() + "</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hints;
    }
}
