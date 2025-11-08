package smartoffice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Requirement 8 (I/O - File Handling):
 * This class handles all file-based logging for user activity and device history,
 * fulfilling requirement 5b ("Log all user activity...").
 * This class also demonstrates Requirement 11 (Vararg Overloading).
 */
public class OfficeLogger {
    private static final String LOG_FILE = "smart_office_activity.log";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * Logs a single activity to the log file.
     * @param activity The string message to log.
     */
    public void logActivity(String activity) {
        // Requirement 8 (I/O): Using BufferedWriter and FileWriter for file I/O.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime now = LocalDateTime.now();
            String logEntry = String.format("[%s] %s%n", dtf.format(now), activity);
            writer.write(logEntry);
        } catch (IOException e) {
            // Requirement 7 (Exception Handling - Case 2: Standard Exception)
            // Handling a standard I/O exception.
            System.err.println("Error: Could not write to log file: " + e.getMessage());
        }
    }

    /**
     * Requirement 11 (Vararg Overloading - Method 1):
     * Logs multiple event messages at once using varargs.
     * @param events A variable number of event strings to log.
     */
    public void logMultipleEvents(String... events) {
        System.out.println("Logging " + events.length + " events:");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime now = LocalDateTime.now();
            for (String event : events) {
                String logEntry = String.format("[%s] [BATCH] %s%n", dtf.format(now), event);
                writer.write(logEntry);
                System.out.println("...Logged: " + event);
            }
        } catch (IOException e) {
            System.err.println("Error: Could not write to batch log: " + e.getMessage());
        }
    }

    /**
     * A simple overload for logMultipleEvents to satisfy the vararg *overloading* requirement.
     * In a real app, this might log events with a specific priority.
     * @param priority The priority level for this batch of events.
     * @param events A variable number of event strings to log.
     */
    public void logMultipleEvents(int priority, String... events) {
        logActivity("Logging batch with priority " + priority + ":");
        logMultipleEvents(events); // Delegates to the other vararg method
    }
}