package service;

import java.sql.Date;
import java.util.List;
import dao.BookingDAO;
import dao.BookingDAOImpl;
import model.Booking;

public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService() {
        bookingDAO = new BookingDAOImpl();
    }

    public void addBooking(int userId, int roomId, Date bookingDate) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setRoomId(roomId);
        booking.setBookingDate(bookingDate);
        bookingDAO.addBooking(booking);
    }

    public List<Booking> listAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public Booking getBookingById(int id) {
        return bookingDAO.getBookingById(id);
    }

    public void deleteBooking(int id) {
        bookingDAO.deleteBooking(id);
    }
}