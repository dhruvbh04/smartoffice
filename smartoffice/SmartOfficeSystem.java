package smartoffice;

// No more imports from java.util

/**
 * The main class for the Smart Office Management System.
 * Updated to use inheritance (Admin, Manager, Employee) and 'instanceof'.
 */
public class SmartOfficeSystem {
    // Define maximum sizes for our arrays
    private static final int MAX_USERS = 50;
    private static final int MAX_DEVICES = 100;
    private static final int MAX_ROOMS = 20;

    // This array now holds User, Admin, Manager, and Employee objects
    private User[] users;
    private AbstractDevice[] devices;
    private ConferenceRoom[] rooms;
    
    // Keep counters for how many items are *actually* in the arrays
    private int userCount;
    private int deviceCount;
    private int roomCount;
    
    private AttendanceSystem attendanceSystem;
    private OfficeLogger logger;
    private User currentUser; // This can hold any object of type User (Admin, Manager, etc.)

    public SmartOfficeSystem() {
        // Initialize the arrays with their maximum size
        this.users = new User[MAX_USERS];
        this.devices = new AbstractDevice[MAX_DEVICES];
        this.rooms = new ConferenceRoom[MAX_ROOMS];
        
        // Initialize counters to 0
        this.userCount = 0;
        this.deviceCount = 0;
        this.roomCount = 0;
        
        this.logger = new OfficeLogger();
        this.attendanceSystem = new AttendanceSystem(logger);
        this.currentUser = null;
        
        initializeData();
    }

    /**
     * Populates the system with sample data using the new user classes.
     */
    private void initializeData() {
        // Add items to arrays using the new classes
        users[userCount] = new Admin("admin", "admin123");
        userCount++;
        users[userCount] = new Manager("manager", "manager123");
        userCount++;
        users[userCount] = new Employee("emp"); // Uses the default password constructor
        userCount++;

        // Add devices
        devices[deviceCount] = new Light("L1", "Main Office");
        deviceCount++;
        devices[deviceCount] = new AC("A1", "Main Office");
        deviceCount++;
        devices[deviceCount] = new Projector("P1", "Conference Room A");
        deviceCount++;
        devices[deviceCount] = new Light("L2", "Conference Room A");
        deviceCount++;

        // Add rooms
        rooms[roomCount] = new ConferenceRoom("ConfA", 10, true);
        roomCount++;
        rooms[roomCount] = new ConferenceRoom("ConfB", 4, false);
        roomCount++;


        logger.logActivity("System initialized with sample data.");
        logger.logMultipleEvents("SystemStart", "DataLoaded", "Ready");
        
        AbstractDevice ac = findDeviceById("A1");
        if (ac != null) {
            ac.setEnergyUsage("Initial", 2100.0, 2200.0, 2150.0);
        }
    }

    /**
     * Simulates the user login process.
     * This method works perfectly with inheritance due to polymorphism.
     */
    public boolean login(String username, String password) {
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            // user.checkPassword() works if user is Admin, Manager, or Employee
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                this.currentUser = user;
                // user.getRole() will call the specific version from Admin, Manager, etc.
                System.out.println("Login successful. Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
                logger.logActivity("Login success for user: " + username);
                return true;
            }
        }
        
        System.out.println("Invalid username or password.");
        logger.logActivity("Login failed for user: " + username);
        return false;
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

    // --- Helper method to find a device by its ID ---
    private AbstractDevice findDeviceById(String deviceId) {
        for (int i = 0; i < deviceCount; i++) {
            AbstractDevice device = devices[i];
            if (device.getDeviceId().equalsIgnoreCase(deviceId)) {
                return device;
            }
        }
        return null; // Not found
    }
    
    // --- Helper method to find a room by its ID ---
    private ConferenceRoom findRoomById(String roomId) {
        for (int i = 0; i < roomCount; i++) {
            ConferenceRoom room = rooms[i];
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                return room;
            }
        }
        return null; // Not found
    }

    // --- Feature Methods ---

    public void displayAllDeviceStatus() {
        System.out.println("\n--- All Device Status ---");
        for (int i = 0; i < deviceCount; i++) {
            System.out.println(devices[i].getStatus());
        }
    }

    public void controlDevice(String deviceId, String action) {
        AbstractDevice device = findDeviceById(deviceId);
        if (device == null) {
            System.out.println("Error: Device '" + deviceId + "' not found.");
            return;
        }

        // Role-Based Access Check using 'instanceof'
        // This checks if the 'currentUser' object is an instance of the 'Employee' class
        if (currentUser instanceof Employee && device.roomLocation.equals("Main Office")) {
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
        for (int i = 0; i < roomCount; i++) {
            System.out.println(rooms[i].getAvailability());
        }
    }

    public void bookRoom(String roomId, String timeSlot) {
        ConferenceRoom room = findRoomById(roomId);
        if (room == null) {
            System.out.println("Error: Room '" + roomId + "' not found.");
            return;
        }

        try {
            room.bookRoom(currentUser.getUsername(), timeSlot);
            logger.logActivity("User " + currentUser.getUsername() + " booked " + roomId + " for " + timeSlot);
        } catch (RoomUnavailableException e) {
            System.err.println("Booking Failed: " + e.getMessage());
            logger.logActivity("Booking FAILED for " + currentUser.getUsername() + " on " + roomId);
        }
    }

    public void employeeCheckIn() {
        attendanceSystem.checkIn(currentUser.getUsername());
    }

    public void employeeCheckOut() {
        attendanceSystem.checkOut(currentUser.getUsername());
    }

    public void generateAttendanceReport() {
        // Role-Based Access Check using 'instanceof'
        // Admins and Managers can see the full report.
        if (currentUser instanceof Admin || currentUser instanceof Manager) {
            System.out.println(attendanceSystem.generateReport());
            logger.logActivity(currentUser.getUsername() + " generated FULL attendance report.");
        } else {
            // Employees can only see their own report
            System.out.println("--- Your Attendance Report ---");
            System.out.println(attendanceSystem.generateReport(currentUser.getUsername()));
            logger.logActivity(currentUser.getUsername() + " generated OWN attendance report.");
        }
    }

    public void adminOnlyFeature() {
        // Role-Based Access Check using 'instanceof'
        // This checks if the 'currentUser' is NOT an Admin
        if (!(currentUser instanceof Admin)) {
            System.out.println("Access Denied. This feature is for Admins only.");
            logger.logActivity("Access Denied (Admin) for " + currentUser.getUsername());
            return;
        }
        System.out.println("--- Admin-Only System Configuration Panel ---");
        System.out.println("...running admin diagnostics...");
        logger.logActivity(currentUser.getUsername() + " accessed Admin Panel.");
    }
}