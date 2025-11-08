package smartoffice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Represents a user in the Smart Office System.
 * Fulfills the "User Authentication & Role-Based Access" feature.
 * This class also demonstrates Requirement 10 (Overloaded Constructors).
 */
public class User {
    private String username;
    private String hashedPassword;
    private Role role;

    /**
     * Requirement 10 (Overloaded Constructors - Constructor 1):
     * A constructor that takes all essential user details.
     * @param username The user's login name.
     * @param plainTextPassword The user's password in plain text (will be hashed).
     * @param role The user's role (ADMIN, MANAGER, EMPLOYEE).
     */
    public User(String username, String plainTextPassword, Role role) {
        this.username = username;
        // Simulates encryption as required by the prompt
        this.hashedPassword = hashPassword(plainTextPassword);
        this.role = role;
    }

    /**
     * Requirement 10 (Overloaded Constructors - Constructor 2):
     * An alternative constructor, perhaps for creating a guest or temporary employee
     * with a default password.
     * @param username The user's login name.
     * @param role The user's role.
     */
    public User(String username, Role role) {
        this(username, "defaultPass123", role); // Delegates to the other constructor
    }

    // --- Getters ---

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Simulates password-based authentication.
     * @param plainTextPassword The password to check.
     * @return true if the password is correct, false otherwise.
     */
    public boolean checkPassword(String plainTextPassword) {
        String newHash = hashPassword(plainTextPassword);
        return this.hashedPassword.equals(newHash);
    }

    /**
     * Simulates encryption (as per requirement 5a).
     * In a real system, use a proper library with salt.
     * @param password The plain text password.
     * @return A Base64-encoded SHA-256 hash of the password.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Password hashing algorithm not found: " + e.getMessage());
            return password; // Fallback (not secure)
        }
    }
}