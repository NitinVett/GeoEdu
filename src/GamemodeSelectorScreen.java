import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamemodeSelectorScreen extends Screen {
    JButton global;
    JButton continental;
    JButton microNations;
    JButton esc;
    Image plankIMG;
    public GamemodeSelectorScreen(FullScreenUI frame, Screen previous, Player user) {
        super(frame, previous,user);
        global = new JButton("Global");
        continental = new JButton("Continental");
        microNations = new JButton("Micro Nations");
        esc = new JButton();
        global.addActionListener(e -> globalButton());
        continental.addActionListener(e -> continentalButton());
        microNations.addActionListener(e -> microNationButton());
        esc.addActionListener(e -> exitButton());
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("resources/escape.png"));
            plankIMG = ImageIO.read(new File("resources/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());
        this.add(global);
        this.add(continental);
        this.add(microNations);
        this.add(esc);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 3 - width / 5;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        Image scaledImage = plankIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(global,scaledImage,width/60);
        createButtons(continental,scaledImage,width/60);
        createButtons(microNations,scaledImage,width/60);
        global.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        continental.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        microNations.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
        revalidate();
    }
    public void globalButton() {
        swapScreens(new GameTypeSelectorScreen(frame,this,user,"Global Mode",null));
    }
    public void continentalButton() {
        swapScreens(new ContinentalModeSelectorScreen(frame,this,user,"Continental Mode"));
    }
    public void microNationButton() {
       swapScreens(new GameTypeSelectorScreen(frame,this,user,"Micro Nation Mode",null));
    }
    public void exitButton() {
        swapScreens(prev);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("resources/Viner.ttf", 32));
        g.drawString("Select a mode to explore!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
