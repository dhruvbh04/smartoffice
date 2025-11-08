package smartoffice;

/**
 * Requirement 5 (Multiple Inheritance - Interface 2):
 * An interface for objects that can generate reports.
 * This, combined with Loggable, will be used to demonstrate multiple inheritance
 * (via interfaces) in the AttendanceSystem class.
 */
public interface Reportable {
    /**
     * Generates a report.
     * @return A string containing the generated report.
     */
    String generateReport();
}