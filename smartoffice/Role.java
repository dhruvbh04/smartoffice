package smartoffice;

/**
 * An enumeration to represent user roles, ensuring type-safe role management.
 * This supports the "User Authentication & Role-Based Access" feature.
 */
public enum Role {
    ADMIN("Admin"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}