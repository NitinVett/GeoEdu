import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The CreditsScreen class extends the Screen class to display a custom credits page within the application.
 * It features a decorative background, images of a ship and a treasure chest, and a list of names to credit
 * the contributors of the project. This screen is intended to visually enrich the application by acknowledging
 * contributions in a visually engaging way.
 */
public class CreditsScreen extends Screen {
    /**
     * JLabels for creditText, creditBackgroundLabel, shipLabel, treasureChestLabel
     */
    JLabel creditText, creditBackgroundLabel, shipLabel, treasureChestLabel;
    /**
     * BufferedImages for creditBackgroundIMG, shipIMG, treasureChestIMG
     */
    private BufferedImage creditBackgroundIMG, shipIMG, treasureChestIMG;
    /**
     * Images for resizedCreditBackgroundIMG, resizedShipIMG, resizedTreasureChestIMG
     */
    private Image resizedCreditBackgroundIMG, resizedShipIMG, resizedTreasureChestIMG;

    /**
     * Constructs a new CreditsScreen instance. It loads the necessary images and sets up the labels
     * for displaying credits and decorative elements.
     *
     * @param frame    The FullScreenUI object that the CreditsScreen is part of, providing the frame
     *                 for displaying the UI components.
     * @param previous The Screen object that was displayed before transitioning to the CreditsScreen,
     *                 allowing for navigation back to the previous state.
     */
    public CreditsScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        creditText = new JLabel();
        creditText.setText("<html><body>By:<br>Stefan Baggieri<br>Nitin Vettiankal<br>Amaan Hafeez<br>Gary Han<br>Saleh Farrukh</body></html>");
        this.add(creditText);
        try {
            creditBackgroundIMG = ImageIO.read(getClass().getResourceAsStream("/scroll.png"));
            shipIMG = ImageIO.read(getClass().getResourceAsStream("/ship.png"));
            treasureChestIMG = ImageIO.read(getClass().getResourceAsStream("/treasureChest.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        creditBackgroundLabel = new JLabel();
        shipLabel = new JLabel();
        treasureChestLabel = new JLabel();
        repaint();
    }

    /**
     * Sets up and positions the components on the CreditsScreen, including scaling images to fit the
     * screen size and arranging text and images for an aesthetically pleasing layout.
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
        creditText.setFont(loadFont("/Viner.ttf", 30));
        this.add(shipLabel);
        this.add(treasureChestLabel);
        this.add(creditBackgroundLabel);
        repaint();
        revalidate();
    }

    /**
     * Custom painting method for the CreditsScreen. It is responsible for rendering the screen's
     * components, including background, text, and images. This method ensures that the components
     * are correctly positioned and displayed on the screen.
     *
     * @param g The Graphics object used for drawing operations.
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
