/**
 * This class represents a user. Each user has a unique username and password
 * @author Tianyi Han
 * @version 1.0
 */
public class User {
    /**
     * username of user
     */
    private String username;
    /**
     * password of user
     */
    private String password;

    /**
     * Constructor creates a user with the given username and password
     * @param username username of the user
     * @param password password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        CsvHandler.addUser(username, password);
    }

    /**
     * Mutator method to set the username of the user
     *
     * @param username username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Mutator method to set the password of the user
     * @param password password of the user
     */
    public void setPassword(String password) {
        this.password = password;
        CsvHandler.changePassword(username, password);
    }

    /**
     * Accessor method to get the username of the user
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Accessor method to get the password of the user
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }
}
