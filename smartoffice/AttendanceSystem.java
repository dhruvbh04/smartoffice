package smartoffice;

// No imports from java.util

/**
 * Manages employee attendance (Heavily Simplified).
 * Uses a simple fixed-size array to log check-in/out events as strings.
 */
public class AttendanceSystem implements Reportable, Loggable {

    // Define a maximum size for the attendance log
    private static final int MAX_LOGS = 1000;
    
    // Use a simple array of strings to log events
    private String[] attendanceLog;
    private int logCount; // Counter for the number of logs
    
    private OfficeLogger logger;

    public AttendanceSystem(OfficeLogger logger) {
        this.attendanceLog = new String[MAX_LOGS];
        this.logCount = 0;
        this.logger = logger;
    }

    /**
     * Simulates an employee checking in.
     * @param employeeId The ID of the employee.
     */
    public void checkIn(String employeeId) {
        String message = employeeId + " checked IN";
        System.out.println(message);
        
        // Add to our simple log array if there is space
        if (logCount < MAX_LOGS) {
            attendanceLog[logCount] = message;
            logCount++;
        }
        
        logEvent(message); // Log to file
    }

    /**
     * Simulates an employee checking out.
     * @param employeeId The ID of the employee.
     */
    public void checkOut(String employeeId) {
        String message = employeeId + " checked OUT";
        System.out.println(message);
        
        // Add to our simple log array if there is space
        if (logCount < MAX_LOGS) {
            attendanceLog[logCount] = message;
            logCount++;
        }
        
        logEvent(message); // Log to file
    }

    /**
     * Generates a full attendance report for all employees.
     * @return A string containing the full report.
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder("--- Full Attendance Report ---\n");
        if (logCount == 0) {
            report.append("No attendance records found.\n");
            return report.toString();
        }

        // Loop from 0 to logCount
        for (int i = 0; i < logCount; i++) {
            report.append("  - ").append(attendanceLog[i]).append("\n");
        }
        return report.toString();
    }

    /**
     * Generates an attendance report for a *specific* employee.
     * @param employeeId The ID of the employee to report on.
     * @return A string containing the employee's specific report.
     */
    public String generateReport(String employeeId) {
        StringBuilder report = new StringBuilder();
        boolean found = false;

        // Loop from 0 to logCount
        for (int i = 0; i < logCount; i++) {
            String entry = attendanceLog[i];
            if (entry.contains(employeeId)) {
                report.append("  - ").append(entry).append("\n");
                found = true;
            }
        }

        if (!found) {
            report.append("  No records found for employee ").append(employeeId).append(".\n");
        }
        return report.toString();
    }

    /**
     * Implementation for the Loggable interface.
     */
    public void logEvent(String event) {
        logger.logActivity("ATTENDANCE: " + event);
    }
}