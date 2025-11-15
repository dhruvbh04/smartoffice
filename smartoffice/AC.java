package smartoffice;

/**
 * Requirement 4 (Hierarchical Inheritance - Child 2):
 * A concrete class for Air Conditioners.
 */
public class AC extends AbstractDevice {
    private int temperature; 

    public AC(String deviceId, String roomLocation) {
        super(deviceId, roomLocation);
        this.temperature = 24; 
    }

    // Removed @Override
    public void turnOn() {
        super.turnOn();
        setEnergyUsage();
        System.out.println(deviceId + " set to " + this.temperature + "°C.");
    }

    // Removed @Override
    public void turnOff() {
        super.turnOff();
    }

    public void setTemperature(int temp) {
        if (this.isOn) {
            this.temperature = temp;
            setEnergyUsage();
            System.out.println(deviceId + " temperature set to " + this.temperature + "°C.");
        } else {
            System.out.println(deviceId + " is off. Turn it on to set temperature.");
        }
    }

    // Removed @Override
    public String getStatus() {
        String parentStatus = super.getStatus();
        return parentStatus + " | Target Temp: " + this.temperature + "°C";
    }

    // Removed @Override
    protected void setEnergyUsage() {
        if (!this.isOn) {
            this.energyUsage = 0.0;
        } else {
            if (this.temperature < 24) {
                this.energyUsage = 2000.0 + (24 - this.temperature) * 100.0;
            } else {
                this.energyUsage = 2000.0; 
            }
        }
    }
}