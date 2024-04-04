import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class ScreenTest {

    private Screen screen;

    @BeforeEach
    void setUp() throws IOException {
        FullScreenUI frame = new FullScreenUI();
        screen = new Screen(frame, null);
    }

    @Test
    void testLoadBackgroundImage() {
        assertNotNull(screen.backgroundImage);
    }

    @Test
    void testLoadCustomCursors() {
        assertNotNull(screen.defaultCursor);
        assertNotNull(screen.customCursor);
    }

    @Test
    void testSetCustomCursor() {
        screen.setCustomCursor();
        assertEquals(screen.customCursor, screen.frame.getCursor());
    }

    @Test
    void testSetDefaultCursor() {
        screen.setDefaultCursor();
        assertEquals(screen.defaultCursor, screen.frame.getCursor());
    }

    @Test
    void testSwapScreens() throws IOException {
        Screen panel = new Screen(new FullScreenUI(),null,null);
        screen.swapScreens(panel);
        assertEquals(panel, screen.frame.getContentPane());
    }

    @Test
    void testSettingsButton() {
        JButton settingsButton = screen.settings;
        assertNotNull(settingsButton);
        settingsButton.doClick();
        assertTrue(screen.frame.getContentPane() instanceof SettingScreen); // Assuming SettingsScreen is the next screen
    }

    @Test
    void testDisplayErrorMessage() {
        String errorMessage = "Error!";
        screen.displayErrorMessage(errorMessage);
        Component[] components = screen.getComponents();
        boolean labelFound = false;
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().equals(errorMessage)) {
                    labelFound = true;
                    break;
                }
            }
        }
        assertTrue(labelFound);
    }

    @Test
    void testLoadFont() {
        Font font = screen.loadFont("resources/Viner Hand ITC.ttf", 24f);
        assertNotNull(font);
    }



}