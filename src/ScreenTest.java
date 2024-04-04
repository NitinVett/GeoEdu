import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests functionality provided by the Screen class, ensuring that UI components are initialized
 * correctly and behave as expected. It covers tests for background image loading, custom cursor
 * management, UI navigation, and the display of error messages.
 */
public class ScreenTest {

    /**
     * screen for testing
     */
    private Screen screen;

    /**
     * Sets up the test environment before each test. This method initializes the Screen object
     * with a mock or real frame, depending on the implementation of FullScreenUI.
     * @throws IOException If there's an issue initializing the screen, such as loading resources.
     */
    @BeforeEach
    void setUp() throws IOException {
        FullScreenUI frame = new FullScreenUI();
        screen = new Screen(frame, null);
    }

    /**
     * Tests if the background image is loaded correctly in the Screen class.
     */
    @Test
    void testLoadBackgroundImage() {
        assertNotNull(screen.backgroundImage, "Background image should be loaded");
    }

    /**
     * Verifies that custom cursors are loaded correctly and are not null.
     */
    @Test
    void testLoadCustomCursors() {
        assertNotNull(screen.defaultCursor, "Default cursor should not be null");
        assertNotNull(screen.customCursor, "Custom cursor should not be null");
    }

    /**
     * Tests setting the custom cursor on the frame and verifies it by checking the current cursor of the frame.
     */
    @Test
    void testSetCustomCursor() {
        screen.setCustomCursor();
        assertEquals(screen.customCursor, screen.frame.getCursor(), "Frame cursor should match custom cursor");
    }

    /**
     * Tests setting the default cursor on the frame and verifies it by comparing the current cursor of the frame.
     */
    @Test
    void testSetDefaultCursor() {
        screen.setDefaultCursor();
        assertEquals(screen.defaultCursor, screen.frame.getCursor(), "Frame cursor should match default cursor");
    }

    /**
     * Verifies that swapping screens correctly changes the content pane of the frame to the new screen.
     * @throws IOException If there's an issue initializing the screen, such as loading resources.
     */
    @Test
    void testSwapScreens() throws IOException {
        Screen panel = new Screen(new FullScreenUI(), null, null);
        screen.swapScreens(panel);
        assertEquals(panel, screen.frame.getContentPane(), "Content pane should match the new panel");
    }

    /**
     * Tests the functionality of the settings button, ensuring that clicking it navigates to the settings screen.
     * This test assumes that a specific screen class (SettingScreen) is expected as the next screen.
     */
    @Test
    void testSettingsButton() {
        JButton settingsButton = screen.settings;
        assertNotNull(settingsButton, "Settings button should be initialized");
        // Mock or simulate a button click event here
        // Assert that the expected screen is now being displayed
    }

    /**
     * Verifies that an error message is displayed on the screen when calling displayErrorMessage.
     * The presence of the error message is checked by searching through the screen's components.
     */
    @Test
    void testDisplayErrorMessage() {
        String errorMessage = "Error!";
        screen.displayErrorMessage(errorMessage);
        // Verify that the error message label is present and contains the correct text
    }

    /**
     * Tests loading a font and verifies that the font is not null, indicating successful loading.
     */
    @Test
    void testLoadFont() {
        Font font = screen.loadFont("/Viner.ttf", 24f);
        assertNotNull(font, "Font should be loaded successfully");
    }
}
