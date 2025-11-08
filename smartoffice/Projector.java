package smartoffice;

/**
 * Requirement 4 (Hierarchical Inheritance - Child 3):
 * A concrete class for Projectors, inheriting from AbstractDevice.
 */
public class Projector extends AbstractDevice {

    private String inputSource;

    public Projector(String deviceId, String roomLocation) {
        super(deviceId, roomLocation);
        this.inputSource = "HDMI1"; // Default source
    }

    @Override
    public void turnOn() {
        super.turnOn();
        setEnergyUsage();
        System.out.println(deviceId + " is displaying from " + this.inputSource + ".");
    }

    @Override
    public void turnOff() {
        super.turnOff();
    }

    public void setInputSource(String source) {
        this.inputSource = source;
        if (this.isOn) {
            System.out.println(deviceId + " source switched to " + this.inputSource + ".");
        }
    }

    @Override
    public String getStatus() {
        String parentStatus = super.getStatus();
        return parentStatus + " | Input: " + this.inputSource;
    }

    /**
     * Implementation of the abstract method from AbstractDevice.
     * Sets a fixed energy usage for the projector when on.
     */
    @Override
    protected void setEnergyUsage() {
        if (this.isOn) {
            this.energyUsage = 350.0; // Fixed wattage for a projector
        } else {
            this.energyUsage = 0.0;
        }
    }
}