package smartoffice;

import java.util.Objects; 
// We still need Objects for the .hash() method in the Booking class

/**
 * Manages the booking and scheduling of a conference room.
 * Simplified to use a fixed-size array for bookings.
 */
public class ConferenceRoom {
    private String roomId;
    private int capacity;
    private boolean hasProjector;
    
    // Use a fixed-size array for bookings
    private static final int MAX_BOOKINGS = 50;
    private Booking[] bookings;
    private int bookingCount; // Counter for actual number of bookings

    /**
     * Requirement 1 (Nested Class - Non-Static Inner Class):
     * A Booking object is tied to a ConferenceRoom instance.
     */
    public class Booking {
        private String employeeId;
        private String timeSlot; 
        private long checkInTime; 

        public Booking(String employeeId, String timeSlot) {
            this.employeeId = employeeId;
            this.timeSlot = timeSlot;
            this.checkInTime = 0; // 0 means not checked in
        }

        public String getTimeSlot() {
            return timeSlot;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void checkIn() {
            this.checkInTime = System.currentTimeMillis();
            System.out.println(employeeId + " checked into " + roomId + " for " + timeSlot);
        }

        public boolean isCheckedIn() {
            return this.checkInTime != 0;
        }

        // Removed @Override
        public String toString() {
            return String.format("Slot: %s (Booked by: %s, Checked In: %s)",
                    timeSlot, employeeId, (isCheckedIn() ? "Yes" : "No"));
        }

        // Removed @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Booking booking = (Booking) o;
            return timeSlot.equals(booking.timeSlot);
        }

        // Removed @Override
        public int hashCode() {
            // This import is okay as it's not a collection class
            return Objects.hash(timeSlot);
        }
    } // --- End of nested Booking class ---

    // --- ConferenceRoom methods ---

    public ConferenceRoom(String roomId, int capacity, boolean hasProjector) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.bookings = new Booking[MAX_BOOKINGS]; // Initialize array
        this.bookingCount = 0; // Initialize counter
    }

    public String getRoomId() {
        return roomId;
    }

    /**
     * Checks if the room is available for a given time slot.
     * @param timeSlot The desired time slot.
     * @return true if available, false otherwise.
     */
    public boolean isAvailable(String timeSlot) {
        // Loop from 0 to bookingCount
        for (int i = 0; i < bookingCount; i++) {
            Booking booking = bookings[i];
            if (booking.getTimeSlot().equals(timeSlot)) {
                return false; // Slot is taken
            }
        }
        return true; // Slot is free
    }

    /**
     * Books the room for a user.
     * @param employeeId The ID of the employee booking the room.
     * @param timeSlot The desired time slot.
     * @return The new Booking object.
     * @throws RoomUnavailableException If the room is already booked or full.
     */
    public Booking bookRoom(String employeeId, String timeSlot) throws RoomUnavailableException {
        if (!isAvailable(timeSlot)) {
            throw new RoomUnavailableException("Room " + roomId + " is already booked for " + timeSlot);
        }
        
        // Check if the booking array is full
        if (bookingCount >= MAX_BOOKINGS) {
            System.out.println("Error: Room " + roomId + " has no more available booking slots.");
            // We can re-use the exception
            throw new RoomUnavailableException("Room " + roomId + " cannot accept more bookings (system limit reached).");
        }

        Booking newBooking = new Booking(employeeId, timeSlot);
        // Add to the array and increment the counter
        bookings[bookingCount] = newBooking;
        bookingCount++;
        
        System.out.println("Room " + roomId + " successfully booked for " + timeSlot + " by " + employeeId);
        return newBooking;
    }

    /**
     * Simulates "Auto-release unused reservations".
     * This logic is complex with arrays, as it requires shifting elements.
     */
    public void autoReleaseUnusedBookings() {
        System.out.println("Checking for unused bookings in " + roomId + "...");
        
        int removedCount = 0;
        int i = 0;
        while (i < bookingCount) {
            if (!bookings[i].isCheckedIn()) {
                // This booking needs to be removed.
                removedCount++;
                
                // Shift all subsequent elements one position to the left.
                for (int j = i; j < bookingCount - 1; j++) {
                    bookings[j] = bookings[j + 1];
                }
                
                // Clear the last (now duplicate) element
                bookings[bookingCount - 1] = null; 
                bookingCount--; // Reduce the total count
                
                // Don't increment 'i' because the new element at 'i' needs to be checked
            } else {
                // This booking is fine, move to the next one
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
        StringBuilder sb = new StringBuilder();
        sb.append("Bookings for ").append(roomId).append(":\n");
        
        // Loop from 0 to bookingCount
        for (int i = 0; i < bookingCount; i++) {
            sb.append("  - ").append(bookings[i].toString()).append("\n");
        }
        return sb.toString();
    }
}