package smartoffice;

/**
 * Requirement 4 (Hierarchical Inheritance - Child 1):
 * A concrete class for Lights, inheriting from AbstractDevice.
 */
public class Light extends AbstractDevice {

    private int brightness; // Percentage

    public Light(String deviceId, String roomLocation) {
        super(deviceId, roomLocation);
        this.brightness = 0;
    }

    @Override
    public void turnOn() {
        super.turnOn(); // Calls the parent method
        this.brightness = 100; // Default to full brightness
        setEnergyUsage();
    }

    @Override
    public void turnOff() {
        super.turnOff(); // Calls the parent method
        this.brightness = 0;
    }

    /**
     * Sets the brightness of the light.
     * @param level Brightness level from 0 (off) to 100 (full).
     */
    public void setBrightness(int level) {
        if (level <= 0) {
            turnOff();
        } else {
            this.isOn = true;
            this.brightness = Math.min(level, 100); // Cap at 100
            setEnergyUsage();
            System.out.println(deviceId + " brightness set to " + this.brightness + "%.");
        }
    }

    @Override
    public String getStatus() {
        String parentStatus = super.getStatus();
        return parentStatus + " | Brightness: " + this.brightness + "%";
    }

    /**
     * Implementation of the abstract method from AbstractDevice.
     * Sets energy usage based on brightness.
     */
    @Override
    protected void setEnergyUsage() {
        if (!this.isOn) {
            this.energyUsage = 0.0;
        } else {
            // Energy usage is proportional to brightness (e.g., max 60W)
            this.energyUsage = 60.0 * (this.brightness / 100.0);
        }
    }
}