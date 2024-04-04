import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code UserTest} class contains a set of unit tests for the {@link User} class.
 * These tests verify various functionalities related to username and password management.
 *
 * <p>Each test method in this class is focused on testing a specific behavior of the {@link User} class:
 * <ul>
 *     <li>{@link #setUsername()} tests whether the {@code setUsername} method correctly updates the username of a {@code User} object.</li>
 *     <li>{@link #setPassword()} tests whether the {@code setPassword} method correctly updates the password of a {@code User} object.</li>
 *     <li>{@link #getUsername()} tests whether the {@code getUsername} method correctly retrieves the username of a {@code User} object.</li>
 *     <li>{@link #getPassword()} tests whether the {@code getPassword} method correctly retrieves the password of a {@code User} object.</li>
 * </ul>
 *
 * <p>Each test method is annotated with {@code @Test} indicating that it should be executed by a testing framework such as JUnit.
 * The {@code assertEquals} statements within each method verify that the actual output matches the expected output.
 *
 * <p>Developers can use these tests to ensure that the basic functionalities of the {@link User} class are working as expected.
 *
 * @author [Your Name]
 * @version 1.0
 */
class UserTest {

    /**
     * Test whether the setUsername method correctly updates the username of a User object.
     */
    @Test
    void setUsername() {
        // Arrange
        String initialUsername = "initialUser";
        User user = new User(initialUsername, "password123");

        // Act
        user.setUsername("newUsername");

        // Assert
        assertEquals("newUsername", user.getUsername());
    }

    /**
     * Test whether the setPassword method correctly updates the password of a User object.
     */
    @Test
    void setPassword() {
        // Arrange
        String initialPassword = "initialPassword";
        String newPassword = "newPassword123";
        User user = new User("username", initialPassword);

        // Act
        user.setPassword(newPassword);

        // Assert
        assertEquals(newPassword, user.getPassword());
    }

    /**
     * Test whether the getUsername method correctly retrieves the username of a User object.
     */
    @Test
    void getUsername() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "password123");

        // Act
        String retrievedUsername = user.getUsername();

        // Assert
        assertEquals(username, retrievedUsername);
    }

    /**
     * Test whether the getPassword method correctly retrieves the password of a User object.
     */
    @Test
    void getPassword() {
        // Arrange
        String password = "password123";
        User user = new User("username", password);

        // Act
        String retrievedPassword = user.getPassword();

        // Assert
        assertEquals(password, retrievedPassword);
    }
}