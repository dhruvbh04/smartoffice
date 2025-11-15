package smartoffice;

/**
 * Requirement 3 (Interface):
 * Defines the basic controls for any smart device.
 */
public interface Controllable {
    void turnOn();
    void turnOff();
    String getStatus();
}