package smartoffice;

/**
 * Requirement 7 (Exception Handling - Case 1: Custom Exception):
 * A custom exception thrown when a user tries to book a conference room
 * that is already reserved for the requested time slot.
 */
public class RoomUnavailableException extends Exception {
    /**
     * Constructor for the custom exception.
     * @param message A string detailing why the room is unavailable.
     */
    public RoomUnavailableException(String message) {
        super(message);
    }
}