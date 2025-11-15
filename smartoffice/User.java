package smartoffice;

/**
 * Represents a user in the Smart Office System.
 * Simplified to use plain text passwords and String roles.
 * This class also demonstrates Requirement 10 (Overloaded Constructors).
 */
public class User {
    private String username;
    private String password; // Changed from hashedPassword
    private String role; // Changed from Role enum to String

    /**
     * Requirement 10 (Overloaded Constructors - Constructor 1):
     * A constructor that takes all essential user details.
     * @param username The user's login name.
     * @param password The user's password in plain text.
     * @param role The user's role (e.g., "ADMIN", "MANAGER", "EMPLOYEE").
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Requirement 10 (Overloaded Constructors - Constructor 2):
     * An alternative constructor for creating a default employee.
     * @param username The user's login name.
     * @param role The user's role.
     */
    public User(String username, String role) {
        this(username, "defaultPass123", role); // Delegates to the other constructor
    }

    // --- Getters ---

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    /**
     * Simulates password-based authentication.
     * @param passwordToCheck The password to check.
     * @return true if the password is correct, false otherwise.
     */
    public boolean checkPassword(String passwordToCheck) {
        // Simple plain text comparison
        return this.password.equals(passwordToCheck);
    }

    // The hashPassword method has been removed.
}