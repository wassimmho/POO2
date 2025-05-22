package model;

import controller.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Broadcast {
    public Movie movie;
    public Theater Room;
    public LocalDate Date;
    
    public ArrayList<Ticket> tickets;

    public Broadcast(Movie movie, Theater Room, LocalDate Date) {
        this.movie = movie;
        this.Room = Room;
        this.Date = Date;
        this.tickets = new ArrayList<>();
        loadTicketsFromDatabase();
    }

    public void loadTicketsFromDatabase() {
        String sql = "SELECT t.*, s.SeatNumber, s.IsVip " +
                    "FROM tickets t " +
                    "JOIN seats s ON t.SeatID = s.SeatID " +
                    "WHERE t.BroadcastID = (SELECT BroadcastID FROM broadcasts WHERE MovieID = ? AND TheaterID = ? AND BroadcastDate = ?) " +
                    "AND t.UserID IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movie.id);
            pstmt.setInt(2, Room.TheaterId);
            pstmt.setDate(3, java.sql.Date.valueOf(Date));
            
            ResultSet rs = pstmt.executeQuery();
            
            tickets.clear();
            
            while (rs.next()) {
                // Create seat object
                Seats seat = new Seats(rs.getInt("SeatNumber"), 
                    rs.getBoolean("IsVip") ? Seats.SeatType.VIP : Seats.SeatType.REGULAR);
                
                // Create ticket
                Ticket ticket = new Ticket(seat, 
                    seat.Type == Seats.SeatType.VIP ? Ticket.TicketType.VIP : Ticket.TicketType.Normal, 
                    this);
                
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading tickets from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void reloadTicketsFromDatabase() {
        loadTicketsFromDatabase();
    }
}
