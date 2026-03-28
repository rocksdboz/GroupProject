package model;

import java.sql.Date;

public class Booking {
    private int id;
    private int userId;
    private int roomId;
    private Date bookingDate;

    public Booking() {}
    public Booking(int id, int userId, int roomId, Date bookingDate) {
        this.id = id; this.userId = userId; this.roomId = roomId; this.bookingDate = bookingDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
}