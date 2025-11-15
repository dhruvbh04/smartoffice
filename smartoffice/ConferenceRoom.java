package smartoffice;

import java.util.Objects; 

/**
 * Manages the booking and scheduling of a conference room.
 * Simplified to use a fixed-size array for bookings.
 * Removed StringBuilder.
 */
public class ConferenceRoom {
    private String roomId;
    private int capacity;
    private boolean hasProjector;
    
    private static final int MAX_BOOKINGS = 50;
    private Booking[] bookings;
    private int bookingCount;

    /**
     * Requirement 1 (Nested Class - Non-Static Inner Class):
     */
    public class Booking {
        private String employeeId;
        private String timeSlot; 
        private boolean isCheckedIn; // Simplified from long to boolean

        public Booking(String employeeId, String timeSlot) {
            this.employeeId = employeeId;
            this.timeSlot = timeSlot;
            this.isCheckedIn = false; 
        }

        public String getTimeSlot() {
            return timeSlot;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void checkIn() {
            this.isCheckedIn = true; 
            System.out.println(employeeId + " checked into " + roomId + " for " + timeSlot);
        }

        public boolean isCheckedIn() {
            return this.isCheckedIn;
        }

        public String toString() {
            return String.format("Slot: %s (Booked by: %s, Checked In: %s)",
                    timeSlot, employeeId, (isCheckedIn() ? "Yes" : "No"));
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Booking booking = (Booking) o;
            return timeSlot.equals(booking.timeSlot);
        }

        public int hashCode() {
            return Objects.hash(timeSlot);
        }
    } // --- End of nested Booking class ---

    // --- ConferenceRoom methods ---

    public ConferenceRoom(String roomId, int capacity, boolean hasProjector) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.bookings = new Booking[MAX_BOOKINGS];
        this.bookingCount = 0;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isAvailable(String timeSlot) {
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking.getTimeSlot().equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    public Booking bookRoom(String employeeId, String timeSlot) throws RoomUnavailableException {
        if (!isAvailable(timeSlot)) {
            throw new RoomUnavailableException("Room " + roomId + " is already booked for " + timeSlot);
        }
        
        if (bookingCount >= MAX_BOOKINGS) {
            throw new RoomUnavailableException("Room " + roomId + " cannot accept more bookings (system limit reached).");
        }

        Booking newBooking = new Booking(employeeId, timeSlot);
        bookings[bookingCount] = newBooking;
        bookingCount++;
        
        System.out.println("Room " + roomId + " successfully booked for " + timeSlot + " by " + employeeId);
        return newBooking;
    }

    public void autoReleaseUnusedBookings() {
        System.out.println("Checking for unused bookings in " + roomId + "...");
        
        int removedCount = 0;
        int i = 0;
        while (i < bookingCount) {
            if (!bookings[i].isCheckedIn()) { 
                removedCount++;
                for (int j = i; j < bookingCount - 1; j++) {
                    bookings[j] = bookings[j + 1];
                }
                bookings[bookingCount - 1] = null; 
                bookingCount--;
            } else {
                i++;
            }
        }

        if (removedCount > 0) {
            System.out.println("Auto-released " + removedCount + " unused bookings.");
        } else {
            System.out.println("No unused bookings to release.");
        }
    }

    /**
     * Gets a string showing the room's availability.
     * @return A string listing all current bookings.
     */
    public String getAvailability() {
        if (bookingCount == 0) {
            return "Room " + roomId + " (Cap: " + capacity + ") is completely free.";
        }
        
        // --- CHANGED ---
        // Start with a base string instead of StringBuilder
        String report = "Bookings for " + roomId + ":\n";
        
        // Loop and append to the string using +=
        for (int i = 0; i < bookingCount; i++) {
            report += "  - " + bookings[i].toString() + "\n";
        }
        return report;
    }
}