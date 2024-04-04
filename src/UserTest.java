import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

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