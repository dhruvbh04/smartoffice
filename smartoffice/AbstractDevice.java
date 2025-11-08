package smartoffice;

/**
 * Requirement 2 (Abstract Class):
 * An abstract class representing a generic office device.
 * It implements the Controllable interface, providing common functionality
 * and fields that all specific devices (Light, AC) will inherit.
 */
public abstract class AbstractDevice implements Controllable {
    protected String deviceId;
    protected String roomLocation;
    protected boolean isOn;

    // Requirement 12 (Wrappers): Using the Double wrapper class.
    protected Double energyUsage; // in Watts

    /**
     * Constructor for the abstract device.
     * @param deviceId A unique identifier for the device.
     * @param roomLocation The room where the device is located.
     */
    public AbstractDevice(String deviceId, String roomLocation) {
        this.deviceId = deviceId;
        this.roomLocation = roomLocation;
        this.isOn = false; // All devices are off by default
        this.energyUsage = 0.0; // Default energy usage
    }

    // --- Implemented methods from Controllable interface (Req 3) ---

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println(deviceId + " in " + roomLocation + " has been turned ON.");
        setEnergyUsage(); // Update energy usage when turned on
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println(deviceId + " in " + roomLocation + " has been turned OFF.");
        this.energyUsage = 0.0; // No energy usage when off
    }

    @Override
    public String getStatus() {
        // Requirement 12 (Wrappers): Using Boolean wrapper's toString method implicitly.
        String status = this.isOn ? "On" : "Off";
        return String.format("Device: %s (%s) | Status: %s | Energy Usage: %.1fW",
                this.deviceId, this.roomLocation, status, this.energyUsage);
    }

    // --- Abstract method to be implemented by subclasses ---
    
    /**
     * Abstract method to set the energy usage.
     * Subclasses (Light, AC) will provide a specific implementation.
     */
    protected abstract void setEnergyUsage();

    // --- Vararg method to satisfy Requirement 11 ---
    
    /**
     * Requirement 11 (Vararg Overloading - Method 2):
     * A vararg method to set energy usage from multiple readings.
     * This is a bit contrived but satisfies the requirement.
     * It will calculate the average of the provided usages.
     * @param usages A variable number of energy readings.
     */
    public void setEnergyUsage(Double... usages) {
        if (usages.length == 0) {
            this.energyUsage = 0.0;
            return;
        }
        double sum = 0;
        for (Double usage : usages) { // Requirement 12 (Wrappers): Auto-unboxing Double to double
            if (usage != null) {
                sum += usage;
            }
        }
        this.energyUsage = sum / usages.length;
        System.out.println("Average energy usage for " + deviceId + " set to: " + this.energyUsage);
    }

    // Overload for setEnergyUsage to satisfy vararg *overloading*
    public void setEnergyUsage(String profile, Double... usages) {
        System.out.println("Setting energy for profile: " + profile);
        setEnergyUsage(usages); // Delegates to the other vararg method
    }

    public String getDeviceId() {
        return deviceId;
    }
}