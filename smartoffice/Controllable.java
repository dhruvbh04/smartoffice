package smartoffice;

/**
 * Requirement 3 (Interface):
 * This interface defines the basic controls for any smart device.
 * It will be implemented by the AbstractDevice class.
 */
public interface Controllable {
    /**
     * Turns the device on.
     */
    void turnOn();

    /**
     * Turns the device off.
     */
    void turnOff();

    /**
     * Gets the current status of the device.
     * @return A string describing the device's status (e.g., "On", "Off").
     */
    String getStatus();
}