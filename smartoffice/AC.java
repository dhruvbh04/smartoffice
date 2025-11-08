package smartoffice;

/**
 * Requirement 4 (Hierarchical Inheritance - Child 2):
 * A concrete class for Air Conditioners, inheriting from AbstractDevice.
 */
public class AC extends AbstractDevice {

    private int temperature; // in Celsius

    public AC(String deviceId, String roomLocation) {
        super(deviceId, roomLocation);
        this.temperature = 24; // Default temp
    }

    @Override
    public void turnOn() {
        super.turnOn();
        setEnergyUsage();
        System.out.println(deviceId + " set to " + this.temperature + "°C.");
    }

    @Override
    public void turnOff() {
        super.turnOff();
    }

    /**
     * Sets the target temperature for the AC.
     * @param temp The target temperature.
     */
    public void setTemperature(int temp) {
        if (this.isOn) {
            this.temperature = temp;
            setEnergyUsage();
            System.out.println(deviceId + " temperature set to " + this.temperature + "°C.");
        } else {
            System.out.println(deviceId + " is off. Turn it on to set temperature.");
        }
    }

    @Override
    public String getStatus() {
        String parentStatus = super.getStatus();
        return parentStatus + " | Target Temp: " + this.temperature + "°C";
    }

    /**
     * Implementation of the abstract method from AbstractDevice.
     * Sets energy usage based on temperature setting.
     */
    @Override
    protected void setEnergyUsage() {
        if (!this.isOn) {
            this.energyUsage = 0.0;
        } else {
            // Simplified energy model: more cooling = more energy
            // (e.g., 2000W base, +100W for every degree below 24)
            if (this.temperature < 24) {
                this.energyUsage = 2000.0 + (24 - this.temperature) * 100.0;
            } else {
                this.energyUsage = 2000.0; // Base usage
            }
        }
    }
}