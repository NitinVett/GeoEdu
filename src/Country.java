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
    private int ID;

    //Provide class with name of country

    public Country (String name){
        //Add logic here that receives an ID number, opens a text file, finds out the name, and returns a country object.
        this.name = name;

    }
    public JLabel getFlag() {

        BufferedImage flag = null;
        try {
            String folderPath = "Country Data/Flags";
            String imagePath = folderPath + "/" + this.getName() + ".png";
            flag = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (flag != null) {
            this.flag = new JLabel(new ImageIcon(flag));
            this.flag.setBounds(0, 0, screenSize.width, screenSize.height);
        }
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
        String hintString = CountryDatabase.hints(name);
        System.out.println(hintString);
        try {
            String[] lines = hintString.split("\n");
            StringBuilder content = new StringBuilder();
            int lineCount = 0;
            for (String line : lines) {
                if (lineCount >= 3) break; // Read only 3 lines
                content.append(line).append("<br>");
                lineCount++;
            }
            hints.setText("<html>" + content.toString() + "</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hints;
    }
}
