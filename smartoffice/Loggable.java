package smartoffice;

/**
 * Requirement 5 (Multiple Inheritance - Interface 1):
 * An interface for objects that can log events.
 * This, combined with Reportable, will be used to demonstrate multiple inheritance
 * (via interfaces) in the AttendanceSystem class.
 */
public interface Loggable {
    /**
     * Logs a specific event.
     * @param event A string describing the event to be logged.
     */
    void logEvent(String event);
}