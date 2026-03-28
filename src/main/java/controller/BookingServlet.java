package controller;

import dao.BookingDAO;
import dao.BookingDAOImpl;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BookingDAO bookingDAO = new BookingDAOImpl();

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

        boolean success = bookingDAO.saveBooking(username, roomNumber, bookingDate);

        response.setContentType("text/html");
        if (success) {
            response.getWriter().println("<h2>Booking successful for Room " + roomNumber + " on " + bookingDate + "</h2>");
        } else {
            response.getWriter().println("<h2>That room is already booked for " + bookingDate + ".</h2>");
        }
        response.getWriter().println("<a href='rooms.jsp'>Back to Rooms</a>");
    }




}