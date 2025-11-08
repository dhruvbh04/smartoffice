package smartoffice;

import java.util.Scanner;

/**
 * Requirement 6 (Package): This file is part of the 'smartoffice' package.
 *
 * This is the main entry point for the Smart Office Management System.
 * It provides the CLI (Command Line Interface) as required by the prompt.
 *
 * Requirement 8 (I/O - Scanner): Uses the Scanner class for user input.
 */
public class Main {

    private static SmartOfficeSystem system = new SmartOfficeSystem();
    
    // Requirement 8 (I/O): Using Scanner for CLI
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Smart Office Management System");

        while (true) {
            if (!system.isUserLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\nPlease log in to continue:");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                System.out.println("Exiting system. Goodbye.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        system.login(username, password);
    }

    private static void showMainMenu() {
        System.out.println("\n--- Smart Office Main Menu ---");
        System.out.println("User: " + system.getCurrentUser().getUsername() + " (" + system.getCurrentUser().getRole() + ")");
        System.out.println("1. View Device Status");
        System.out.println("2. Control Device");
        System.out.println("3. View Room Availability");
        System.out.println("4. Book a Room");
        System.out.println("5. Check-In to Office (Attendance)");
        System.out.println("6. Check-Out from Office (Attendance)");
        System.out.println("7. Generate Attendance Report");
        System.out.println("8. Admin Panel (Admin Only)");
        System.out.println("9. Logout");
        System.out.print("Enter choice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                system.displayAllDeviceStatus();
                break;
            case 2:
                handleDeviceControl();
                break;
            case 3:
                system.displayRoomAvailability();
                break;
            case 4:
                handleRoomBooking();
                break;
            case 5:
                system.employeeCheckIn();
                break;
            case 6:
                system.employeeCheckOut();
                break;
            case 7:
                system.generateAttendanceReport();
                break;
            case 8:
                system.adminOnlyFeature();
                break;
            case 9:
                system.logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleDeviceControl() {
        System.out.print("Enter Device ID (e.g., L1, A1, P1): ");
        String deviceId = scanner.nextLine();
        System.out.print("Enter Action (on/off): ");
        String action = scanner.nextLine();
        system.controlDevice(deviceId, action);
    }

    private static void handleRoomBooking() {
        System.out.print("Enter Room ID (e.g., ConfA, ConfB): ");
        String roomId = scanner.nextLine();
        System.out.print("Enter Time Slot (e.g., 14:00-15:00): ");
        String timeSlot = scanner.nextLine();
        system.bookRoom(roomId, timeSlot);
    }

    /**
     * Helper method to safely get an integer input from the user.
     * This demonstrates Requirement 7 (Exception Handling - Case 2: Standard Exception).
     * @return The integer entered by the user.
     */
    private static int getIntInput() {
        try {
            // Requirement 12 (Wrappers): Using Integer.parseInt.
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            // Requirement 7 (Exception Handling - Standard)
            System.out.println("Invalid input. Please enter a number.");
            return -1; // Return a safe, invalid choice
        }
    }
}