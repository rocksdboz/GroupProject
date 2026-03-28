package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import util.DBConnection;

public class BookingDAOImpl implements BookingDAO {

    private Connection conn;

    public BookingDAOImpl() {
        conn = DBConnection.getConnection();
    }

    @Override
    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (userId, roomId, bookingDate) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getRoomId());
            ps.setDate(3, booking.getBookingDate());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id=?";
        Booking booking = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                booking = new Booking(rs.getInt("id"), rs.getInt("userId"), rs.getInt("roomId"), rs.getDate("bookingDate"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return booking;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Booking(rs.getInt("id"), rs.getInt("userId"), rs.getInt("roomId"), rs.getDate("bookingDate")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public boolean saveBooking(String username, String roomNumber, String bookingDate) {
        boolean result = false;

        String checkSql = "SELECT * FROM bookings WHERE room_number = ? AND booking_date = ?";
        String insertSql = "INSERT INTO bookings (username, room_number, bookingDate) VALUES (?, ?, ?)";

        try (
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                PreparedStatement insertStmt = conn.prepareStatement(insertSql)
                ) {
            checkStmt.setString(1, roomNumber);
            checkStmt.setDate(2, java.sql.Date.valueOf(bookingDate));

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                result = false; //Meaning it's already booked.
            }

            insertStmt.setString(1, username);
            insertStmt.setString(2, roomNumber);
            insertStmt.setDate(3, java.sql.Date.valueOf(bookingDate));

            int rows = insertStmt.executeUpdate();
            result = (rows > 0);
        } catch (SQLException e) { e.printStackTrace(); }
        return result;
    }
}