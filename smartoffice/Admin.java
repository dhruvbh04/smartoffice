package smartoffice;

/**
 * Represents an Admin user.
 * This class inherits from User.
 */
public class Admin extends User {

    /**
     * Constructor for an Admin.
     * @param username The admin's username.
     * @param password The admin's password.
     */
    public Admin(String username, String password) {
        // Call the parent class (User) constructor
        super(username, password);
    }

    /**
     * Implementation of the abstract method from User.
     * @return The role string "ADMIN".
     */
    public String getRole() {
        return "ADMIN";
    }
}