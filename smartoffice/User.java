package smartoffice;

/**
 * Requirement 2 (Abstract Class):
 * Represents a base user in the Smart Office System.
 * This is the parent class for Admin, Manager, and Employee.
 */
public abstract class User {
    // These fields are common to all user types
    protected String username;
    protected String password;

    /**
     * Base constructor for all User types.
     * @param username The user's login name.
     * @param password The user's password in plain text.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Getters ---

    public String getUsername() {
        return username;
    }

    /**
     * An abstract method that forces child classes (Admin, Manager, Employee)
     * to provide their specific role as a String.
     * @return The role name (e.g., "ADMIN", "EMPLOYEE").
     */
    public abstract String getRole();

    /**
     * Simulates password-based authentication.
     * This method is shared by all child classes.
     * @param passwordToCheck The password to check.
     * @return true if the password is correct, false otherwise.
     */
    public boolean checkPassword(String passwordToCheck) {
        // Simple plain text comparison
        return this.password.equals(passwordToCheck);
    }
}