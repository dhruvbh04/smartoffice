package smartoffice;

/**
 * Requirement 2 (Abstract Class):
 * Represents a generic office device.
 */
public abstract class AbstractDevice implements Controllable {
    protected String deviceId;
    protected String roomLocation;
    protected boolean isOn;
    protected Double energyUsage; 

    public AbstractDevice(String deviceId, String roomLocation) {
        this.deviceId = deviceId;
        this.roomLocation = roomLocation;
        this.isOn = false; 
        this.energyUsage = 0.0; 
    }

    // --- Implemented methods from Controllable interface ---

    // Removed @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println(deviceId + " in " + roomLocation + " has been turned ON.");
        setEnergyUsage(); 
    }

    // Removed @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println(deviceId + " in " + roomLocation + " has been turned OFF.");
        this.energyUsage = 0.0; 
    }

    // Removed @Override
    public String getStatus() {
        String status = this.isOn ? "On" : "Off";
        return String.format("Device: %s (%s) | Status: %s | Energy Usage: %.1fW",
                this.deviceId, this.roomLocation, status, this.energyUsage);
    }

    protected abstract void setEnergyUsage();

    public void setEnergyUsage(Double... usages) {
        if (usages.length == 0) {
            this.energyUsage = 0.0;
            return;
        }
        double sum = 0;
        for (Double usage : usages) { 
            if (usage != null) {
                sum += usage;
            }
        }
        this.energyUsage = sum / usages.length;
        System.out.println("Average energy usage for " + deviceId + " set to: " + this.energyUsage);
    }

    public void setEnergyUsage(String profile, Double... usages) {
        System.out.println("Setting energy for profile: " + profile);
        setEnergyUsage(usages); 
    }

    public String getDeviceId() {
        return deviceId;
    }
}