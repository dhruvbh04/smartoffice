package smartoffice;

// No imports from java.util

/**
 * Manages employee attendance (Heavily Simplified).
 * Uses a simple fixed-size array to log check-in/out events as strings.
 * Removed StringBuilder.
 */
public class AttendanceSystem implements Reportable, Loggable {

    private static final int MAX_LOGS = 1000;
    private String[] attendanceLog;
    private int logCount;
    private OfficeLogger logger;

    public AttendanceSystem(OfficeLogger logger) {
        this.attendanceLog = new String[MAX_LOGS];
        this.logCount = 0;
        this.logger = logger;
    }

    public void checkIn(String employeeId) {
        String message = employeeId + " checked IN";
        System.out.println(message);
        
        if (logCount < MAX_LOGS) {
            attendanceLog[logCount] = message;
            logCount++;
        }
        
        logEvent(message);
    }

    public void checkOut(String employeeId) {
        String message = employeeId + " checked OUT";
        System.out.println(message);
        
        if (logCount < MAX_LOGS) {
            attendanceLog[logCount] = message;
            logCount++;
        }
        
        logEvent(message);
    }

    /**
     * Generates a full attendance report for all employees.
     * @return A string containing the full report.
     */
    public String generateReport() {
        // --- CHANGED ---
        // Start with a base string instead of StringBuilder
        String report = "--- Full Attendance Report ---\n";
        
        if (logCount == 0) {
            report += "No attendance records found.\n"; // Use += to append
            return report;
        }

        // Loop and append to the string using +=
        for (int i = 0; i < logCount; i++) {
            report += "  - " + attendanceLog[i] + "\n";
        }
        return report;
    }

    /**
     * Generates an attendance report for a *specific* employee.
     * @param employeeId The ID of the employee to report on.
     * @return A string containing the employee's specific report.
     */
    public String generateReport(String employeeId) {
        // --- CHANGED ---
        // Start with an empty string
        String report = "";
        boolean found = false;

        for (int i = 0; i < logCount; i++) {
            String entry = attendanceLog[i];
            if (entry.contains(employeeId)) {
                report += "  - " + entry + "\n"; // Use += to append
                found = true;
            }
        }

        if (!found) {
            report += "  No records found for employee " + employeeId + ".\n";
        }
        return report;
    }

    /**
     * Implementation for the Loggable interface.
     */
    public void logEvent(String event) {
        logger.logActivity("ATTENDANCE: " + event);
    }
}