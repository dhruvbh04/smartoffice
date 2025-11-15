package smartoffice;

/**
 * Represents an Employee user.
 * This class inherits from User and includes an overloaded constructor.
 */
public class Employee extends User {

    /**
     * Main constructor for an Employee.
     * @param username The employee's username.
     * @param password The employee's password.
     */
    public Employee(String username, String password) {
        // Call the parent class (User) constructor
        super(username, password);
    }

    /**
     * Overloaded constructor for an Employee with a default password.
     * @param username The employee's username.
     */
    public Employee(String username) {
        // Call this class's main constructor with a default password
        this(username, "defaultPass123");
    }

    /**
     * Implementation of the abstract method from User.
     * @return The role string "EMPLOYEE".
     */
    public String getRole() {
        return "EMPLOYEE";
    }
}