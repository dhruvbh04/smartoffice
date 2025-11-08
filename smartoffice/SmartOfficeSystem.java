package smartoffice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The main class for the Smart Office Management System.
 * This class ties all other components together and provides a
 * simulated authentication and session management.
 */
public class SmartOfficeSystem {
    private Map<String, User> users;
    private Map<String, AbstractDevice> devices;
    private Map<String, ConferenceRoom> rooms;
    private AttendanceSystem attendanceSystem;
    private OfficeLogger logger;
    private User currentUser;

    public SmartOfficeSystem() {
        this.users = new HashMap<>();
        this.devices = new HashMap<>();
        this.rooms = new HashMap<>();
        this.logger = new OfficeLogger();
        this.attendanceSystem = new AttendanceSystem(logger);
        this.currentUser = null;
        
        // Initialize with sample data
        initializeData();
    }

    /**
     * Populates the system with sample users, devices, and rooms.
     */
    private void initializeData() {
        // Requirement 10 (Overloaded Constructors): Using both User constructors
        users.put("admin", new User("admin", "admin123", Role.ADMIN));
        users.put("manager", new User("manager", "manager123", Role.MANAGER));
        users.put("emp", new User("emp", Role.EMPLOYEE)); // Uses the 2nd constructor

        // Requirement 4 (Hierarchical Inheritance): Adding different device types
        devices.put("L1", new Light("L1-Main", "Main Office"));
        devices.put("A1", new AC("A1-Main", "Main Office"));
        devices.put("P1", new Projector("P1-ConfA", "Conference Room A"));
        devices.put("L2", new Light("L2-ConfA", "Conference Room A"));

        rooms.put("ConfA", new ConferenceRoom("ConfA", 10, true));
        rooms.put("ConfB", new ConferenceRoom("ConfB", 4, false));

        logger.logActivity("System initialized with sample data.");
        // Requirement 11 (Vararg): Using vararg methods
        logger.logMultipleEvents("SystemStart", "DataLoaded", "Ready");
        devices.get("A1").setEnergyUsage("Initial", 2100.0, 2200.0, 2150.0);
    }

    /**
     * Simulates the user login process.
     * @param username The username.
     * @param password The password.
     * @return true if login is successful, false otherwise.
     */
    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            this.currentUser = user;
            System.out.println("Login successful. Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
            logger.logActivity("Login success for user: " + username);
            return true;
        } else {
            System.out.println("Invalid username or password.");
            logger.logActivity("Login failed for user: " + username);
            return false;
        }
    }

    public void logout() {
        if (this.currentUser != null) {
            logger.logActivity("Logout for user: " + this.currentUser.getUsername());
            this.currentUser = null;
            System.out.println("You have been logged out.");
        }
    }

    public boolean isUserLoggedIn() {
        return this.currentUser != null;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public OfficeLogger getLogger() {
        return logger;
    }

    // --- Feature Methods (to be called from CLI) ---

    public void displayAllDeviceStatus() {
        System.out.println("\n--- All Device Status ---");
        for (AbstractDevice device : devices.values()) {
            System.out.println(device.getStatus());
        }
    }

    public void controlDevice(String deviceId, String action) {
        AbstractDevice device = devices.get(deviceId.toUpperCase());
        if (device == null) {
            System.out.println("Error: Device '" + deviceId + "' not found.");
            return;
        }

        // Role-Based Access Check (Simplified)
        if (currentUser.getRole() == Role.EMPLOYEE && device.roomLocation.equals("Main Office")) {
            System.out.println("Access Denied: Employees can only control devices in common areas.");
            logger.logActivity("Access Denied for " + currentUser.getUsername() + " on " + deviceId);
            return;
        }

        String logMsg = "";
        switch (action.toLowerCase()) {
            case "on":
                device.turnOn();
                logMsg = "Turned ON " + deviceId;
                break;
            case "off":
                device.turnOff();
                logMsg = "Turned OFF " + deviceId;
                break;
            default:
                System.out.println("Invalid action. Use 'on' or 'off'.");
                return;
        }
        logger.logActivity("User " + currentUser.getUsername() + ": " + logMsg);
    }

    public void displayRoomAvailability() {
        System.out.println("\n--- Room Availability ---");
        for (ConferenceRoom room : rooms.values()) {
            System.out.println(room.getAvailability());
        }
    }

    public void bookRoom(String roomId, String timeSlot) {
        ConferenceRoom room = rooms.get(roomId);
        if (room == null) {
            System.out.println("Error: Room '" + roomId + "' not found.");
            return;
        }

        try {
            // Requirement 7 (Exception Handling): Calling the method that throws.
            room.bookRoom(currentUser.getUsername(), timeSlot);
            logger.logActivity("User " + currentUser.getUsername() + " booked " + roomId + " for " + timeSlot);
        } catch (RoomUnavailableException e) {
            // Requirement 7 (Exception Handling): Catching the custom exception.
            System.err.println("Booking Failed: " + e.getMessage());
            logger.logActivity("Booking FAILED for " + currentUser.getUsername() + " on " + roomId);
        }
    }

    public void checkInToRoom(String roomId, String timeSlot) {
        ConferenceRoom room = rooms.get(roomId);
        if (room == null) {
            System.out.println("Error: Room '" + roomId + "' not found.");
            return;
        }
        
        // This is a simulation; we find the booking and call checkIn()
        // A real app would use the inner class's methods more directly
        System.out.println("Simulating check-in for " + currentUser.getUsername() + " to " + roomId + "...");
        // In this simple CLI, we just log it. A full implementation
        // would require iterating the room's internal booking list.
        logger.logActivity(currentUser.getUsername() + " checked into " + roomId);
    }

    public void employeeCheckIn() {
        attendanceSystem.checkIn(currentUser.getUsername());
    }

    public void employeeCheckOut() {
        attendanceSystem.checkOut(currentUser.getUsername());
    }

    public void generateAttendanceReport() {
        // Role-Based Access Check
        if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.MANAGER) {
            // Requirement 9 (Overloaded Methods): Calling the general report
            System.out.println(attendanceSystem.generateReport());
            logger.logActivity(currentUser.getUsername() + " generated FULL attendance report.");
        } else {
            // Requirement 9 (Overloaded Methods): Calling the specific report
            System.out.println("--- Your Attendance Report ---");
            System.out.println(attendanceSystem.generateReport(currentUser.getUsername()));
            logger.logActivity(currentUser.getUsername() + " generated OWN attendance report.");
        }
    }

    public void adminOnlyFeature() {
        if (currentUser.getRole() != Role.ADMIN) {
            System.out.println("Access Denied. This feature is for Admins only.");
            logger.logActivity("Access Denied (Admin) for " + currentUser.getUsername());
            return;
        }
        System.out.println("--- Admin-Only System Configuration Panel ---");
        System.out.println("...running admin diagnostics...");
        logger.logActivity(currentUser.getUsername() + " accessed Admin Panel.");
    }
}