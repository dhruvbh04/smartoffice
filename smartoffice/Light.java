package smartoffice;

/**
 * Requirement 4 (Hierarchical Inheritance - Child 1):
 * A concrete class for Lights.
 */
public class Light extends AbstractDevice {
    private int brightness; 

    public Light(String deviceId, String roomLocation) {
        super(deviceId, roomLocation);
        this.brightness = 0;
    }

    // Removed @Override
    public void turnOn() {
        super.turnOn(); 
        this.brightness = 100; 
        setEnergyUsage();
    }

    // Removed @Override
    public void turnOff() {
        super.turnOff(); 
        this.brightness = 0;
    }

    public void setBrightness(int level) {
        if (level <= 0) {
            turnOff();
        } else {
            this.isOn = true;
            this.brightness = Math.min(level, 100); 
            setEnergyUsage();
            System.out.println(deviceId + " brightness set to " + this.brightness + "%.");
        }
    }

    // Removed @Override
    public String getStatus() {
        String parentStatus = super.getStatus();
        return parentStatus + " | Brightness: " + this.brightness + "%";
    }

    // Removed @Override
    protected void setEnergyUsage() {
        if (!this.isOn) {
            this.energyUsage = 0.0;
        } else {
            this.energyUsage = 60.0 * (this.brightness / 100.0);
        }
    }
}