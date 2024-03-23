public class User {
    private String username;
    private String password;

    /**
     * This method is bad.
     * @param username
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * setter for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
