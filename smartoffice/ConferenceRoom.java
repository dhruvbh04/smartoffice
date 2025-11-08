package smartoffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manages the booking and scheduling of a conference room.
 * This class also demonstrates Requirement 1 (Nested Class).
 */
public class ConferenceRoom {
    private String roomId;
    private int capacity;
    private boolean hasProjector;
    private List<Booking> bookings; // A list of its bookings

    /**
     * Requirement 1 (Nested Class - Non-Static Inner Class):
     * A Booking object is intrinsically tied to a ConferenceRoom instance.
     * It represents a single reservation for this room.
     */
    public class Booking {
        private String employeeId;
        private String timeSlot; // e.g., "10:00-11:00"
        private long checkInTime; // System.currentTimeMillis()

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

        /**
         * Simulates checking into the room, fulfilling the "auto-release" feature.
         */
        public void checkIn() {
            this.checkInTime = System.currentTimeMillis();
            System.out.println(employeeId + " checked into " + roomId + " for " + timeSlot);
        }

        public boolean isCheckedIn() {
            return this.checkInTime != 0;
        }

        @Override
        public String toString() {
            return String.format("Slot: %s (Booked by: %s, Checked In: %s)",
                    timeSlot, employeeId, (isCheckedIn() ? "Yes" : "No"));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Booking booking = (Booking) o;
            // A booking is unique by its time slot within this room
            return timeSlot.equals(booking.timeSlot);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timeSlot);
        }
    } // --- End of nested Booking class ---

    // --- ConferenceRoom methods ---

    public ConferenceRoom(String roomId, int capacity, boolean hasProjector) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.bookings = new ArrayList<>();
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
        for (Booking booking : bookings) {
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
     * @throws RoomUnavailableException If the room is already booked for that slot.
     */
    public Booking bookRoom(String employeeId, String timeSlot) throws RoomUnavailableException {
        if (!isAvailable(timeSlot)) {
            // Requirement 7 (Exception Handling): Throwing the custom exception.
            throw new RoomUnavailableException("Room " + roomId + " is already booked for " + timeSlot);
        }
        Booking newBooking = new Booking(employeeId, timeSlot);
        bookings.add(newBooking);
        System.out.println("Room " + roomId + " successfully booked for " + timeSlot + " by " + employeeId);
        return newBooking;
    }

    /**
     * Simulates the "Auto-release unused reservations" feature.
     * In a real system, this would be run by a scheduler.
     */
    public void autoReleaseUnusedBookings() {
        System.out.println("Checking for unused bookings in " + roomId + "...");
        // This is a simple simulation. A real one would check time.
        // We'll remove any booking that isn't checked in.
        boolean removed = bookings.removeIf(booking -> !booking.isCheckedIn());
        if (removed) {
            System.out.println("Auto-released unused bookings.");
        } else {
            System.out.println("No unused bookings to release.");
        }
    }

    /**
     * Gets a string showing the room's availability.
     * @return A string listing all current bookings.
     */
    public String getAvailability() {
        if (bookings.isEmpty()) {
            return "Room " + roomId + " (Cap: " + capacity + ") is completely free.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Bookings for ").append(roomId).append(":\n");
        for (Booking booking : bookings) {
            sb.append("  - ").append(booking.toString()).append("\n");
        }
        return sb.toString();
    }
}