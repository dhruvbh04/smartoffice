package smartoffice;

/**
 * Represents a Manager user.
 * This class inherits from User.
 */
public class Manager extends User {

    /**
     * Constructor for a Manager.
     * @param username The manager's username.
     * @param password The manager's password.
     */
    public Manager(String username, String password) {
        // Call the parent class (User) constructor
        super(username, password);
    }

    /**
     * Implementation of the abstract method from User.
     * @return The role string "MANAGER".
     */
    public String getRole() {
        return "MANAGER";
    }
}