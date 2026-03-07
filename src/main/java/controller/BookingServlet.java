package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // MySQL connection details
    private String jdbcURL = "jdbc:mysql://localhost:3306/group_project_db";
    private String jdbcUsername = "root";         // MySQL username
    private String jdbcPassword = "";        // MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the logged-in username from session
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.setContentType("text/html");
            response.getWriter().println("<h2>Please login first</h2>");
            response.getWriter().println("<a href='login.jsp'>Go to Login</a>");
            return;
        }

        // Get room number and booking date from the form
        String roomNumber = request.getParameter("room");
        String bookingDate = request.getParameter("date"); // format: yyyy-MM-dd

        boolean success = saveBooking(username, roomNumber, bookingDate);

        response.setContentType("text/html");
        if (success) {
            response.getWriter().println("<h2>Booking successful for Room " + roomNumber + " on " + bookingDate + "</h2>");
        } else {
            response.getWriter().println("<h2>That room is already booked for " + bookingDate + ".</h2>");
        }
        response.getWriter().println("<a href='rooms.jsp'>Back to Rooms</a>");
    }

    private boolean saveBooking(String username, String roomNumber, String bookingDate) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        java.sql.ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            //CHECK IF THIS ROOM IS ALREADY BOOKED
            String checkSql = "SELECT * FROM bookings WHERE room_number = ? AND booking_date = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, roomNumber);
            checkStmt.setDate(2, java.sql.Date.valueOf(bookingDate));

            rs = checkStmt.executeQuery();

            //IF THE ROW EXISTS == ROOM IS BOOKED
            if (rs.next()) {
                return false;
            }

            //ONLY INSERT IF ROOM IS NOT ALREADY BOOKED
            String insertSql = "INSERT INTO bookings (username, room_number, booking_date) VALUES (?, ?, ?)";
            insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, roomNumber);
            insertStmt.setDate(3, java.sql.Date.valueOf(bookingDate));

            int rows = insertStmt.executeUpdate();
            result = (rows > 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (checkStmt != null) checkStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (insertStmt != null) insertStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }


}