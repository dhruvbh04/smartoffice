package smartoffice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages employee attendance, demonstrating multiple requirements.
 *
 * Requirement 5 (Multiple Inheritance): Implements both Reportable and Loggable.
 * Requirement 9 (Overloaded Methods): Provides two `generateReport` methods.
 */
public class AttendanceSystem implements Reportable, Loggable {

    // Requirement 12 (Wrappers): Using String and LocalTime in a Map.
    private Map<String, Map<LocalDate, LocalTime[]>> records; // EmployeeID -> Date -> [CheckIn, CheckOut]
    private OfficeLogger logger;

    public AttendanceSystem(OfficeLogger logger) {
        this.records = new HashMap<>();
        this.logger = logger;
    }

    /**
     * Simulates an employee checking in.
     * @param employeeId The ID of the employee.
     */
    public void checkIn(String employeeId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        records.putIfAbsent(employeeId, new HashMap<>());
        Map<LocalDate, LocalTime[]> employeeLog = records.get(employeeId);

        if (employeeLog.containsKey(today)) {
            System.out.println(employeeId + " is already checked in today.");
        } else {
            employeeLog.put(today, new LocalTime[]{now, null}); // [CheckIn, null]
            String message = employeeId + " checked IN at " + now.format(DateTimeFormatter.ofPattern("HH:mm"));
            System.out.println(message);
            logEvent(message); // Log this event
        }
    }

    /**
     * Simulates an employee checking out.
     * @param employeeId The ID of the employee.
     */
    public void checkOut(String employeeId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Map<LocalDate, LocalTime[]> employeeLog = records.get(employeeId);

        if (employeeLog == null || !employeeLog.containsKey(today)) {
            System.out.println(employeeId + " has not checked in today. Cannot check out.");
        } else {
            LocalTime[] times = employeeLog.get(today);
            if (times[1] != null) {
                System.out.println(employeeId + " is already checked out today.");
            } else {
                times[1] = now; // Set CheckOut time
                String message = employeeId + " checked OUT at " + now.format(DateTimeFormatter.ofPattern("HH:mm"));
                System.out.println(message);
                logEvent(message); // Log this event
            }
        }
    }

    /**
     * Requirement 9 (Overloaded Methods - Method 1):
     * Generates a full attendance report for all employees.
     * This is the implementation for the Reportable interface.
     * @return A string containing the full report.
     */
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder("--- Full Attendance Report ---\n");
        if (records.isEmpty()) {
            report.append("No attendance records found.\n");
            return report.toString();
        }

        for (Map.Entry<String, Map<LocalDate, LocalTime[]>> entry : records.entrySet()) {
            report.append("Employee: ").append(entry.getKey()).append("\n");
            report.append(generateReport(entry.getKey())); // Calls the overloaded method
            report.append("-----------------------------\n");
        }
        return report.toString();
    }

    /**
     * Requirement 9 (Overloaded Methods - Method 2):
     * Generates an attendance report for a *specific* employee.
     * @param employeeId The ID of the employee to report on.
     * @return A string containing the employee's specific report.
     */
    public String generateReport(String employeeId) {
        StringBuilder report = new StringBuilder();
        Map<LocalDate, LocalTime[]> employeeLog = records.get(employeeId);

        if (employeeLog == null || employeeLog.isEmpty()) {
            report.append("  No records found for employee ").append(employeeId).append(".\n");
            return report.toString();
        }

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (Map.Entry<LocalDate, LocalTime[]> dayEntry : employeeLog.entrySet()) {
            LocalTime[] times = dayEntry.getValue();
            String checkIn = (times[0] != null) ? times[0].format(timeFormat) : "N/A";
            String checkOut = (times[1] != null) ? times[1].format(timeFormat) : "N/A";
            report.append(String.format("  - %s: IN: %s, OUT: %s\n",
                    dayEntry.getKey().toString(), checkIn, checkOut));
        }
        return report.toString();
    }

    /**
     * Implementation for the Loggable interface (Req 5).
     * It delegates the logging action to the OfficeLogger.
     * @param event A string describing the event.
     */
    @Override
    public void logEvent(String event) {
        logger.logActivity("ATTENDANCE: " + event);
    }
}