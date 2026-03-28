package dao;

import java.util.List;
import model.Booking;

public interface BookingDAO {
    void addBooking(Booking booking);
    Booking getBookingById(int id);
    List<Booking> getAllBookings();
    void deleteBooking(int id);
    boolean saveBooking(String username, String roomNumber, String bookingDate);
}