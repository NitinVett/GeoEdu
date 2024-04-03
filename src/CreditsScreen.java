import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * CreditsScreen class displays credits and images of ship and treasure chest.
 * Extends Screen class.
 */
public class CreditsScreen extends Screen {
    JLabel creditText, creditBackgroundLabel, shipLabel, treasureChestLabel;
    private BufferedImage creditBackgroundIMG, shipIMG, treasureChestIMG;
    private Image resizedCreditBackgroundIMG, resizedShipIMG, resizedTreasureChestIMG;

    /**
     * Constructor for CreditsScreen.
     *
     * @param frame    FullScreenUI object.
     * @param previous Previous Screen object.
     */
    public CreditsScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        creditText = new JLabel();
        creditText.setText("<html><body>By:<br>Stefan Baggieri<br>Nitin Vettiankal<br>Amaan Hafeez<br>Gary Han<br>Saleh Farrukh</body></html>");
        this.add(creditText);
        try {
            creditBackgroundIMG = ImageIO.read(new File("scroll.png"));
            shipIMG = ImageIO.read(new File("ship.png"));
            treasureChestIMG = ImageIO.read(new File("treasureChest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        creditBackgroundLabel = new JLabel();
        shipLabel = new JLabel();
        treasureChestLabel = new JLabel();
        repaint();
    }

    /**
     * Sets the components for CreditsScreen.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        resizedCreditBackgroundIMG = creditBackgroundIMG.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        creditBackgroundLabel.setBounds(width / 300, height / 35, width, height);
        resizedShipIMG = shipIMG.getScaledInstance(width / 8, height / 8, Image.SCALE_SMOOTH);
        shipLabel.setBounds(width / 5, height / 4, width / 2, height / 2);
        resizedTreasureChestIMG = treasureChestIMG.getScaledInstance(width / 8, height / 8, Image.SCALE_SMOOTH);
        creditBackgroundLabel.setIcon(new ImageIcon(resizedCreditBackgroundIMG));
        shipLabel.setIcon(new ImageIcon(resizedShipIMG));
        treasureChestLabel.setIcon(new ImageIcon(resizedTreasureChestIMG));
        treasureChestLabel.setBounds(width - width / 3, height / 4, width / 2, height / 2);
        creditText.setBounds(width / 3 + width / 10, height / 50, width, height);
        creditText.setFont(new Font("resources/Viner.ttf", Font.PLAIN, 30));
        this.add(shipLabel);
        this.add(treasureChestLabel);
        this.add(creditBackgroundLabel);
        repaint();
        revalidate();
    }

    /**
     * Overrides the paintComponent method to paint components on the screen.
     *
     * @param g Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        remove(settings);
        drawTitle(g2D);
    }
}
