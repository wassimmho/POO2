package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.*;

public class BookingManager {

    
    public MovieManager movieManager;
    public TheaterManager theaterManager;
    public BroadcastManager broadcastManager;
    public ClientManager clientManager;

    public ArrayList<Ticket> bookedTickets;

    public BookingManager() {
        this.bookedTickets = new ArrayList<>();
        loadBookedTicketsFromDatabase();
    }

    private void loadBookedTicketsFromDatabase() {
        String sql = "SELECT t.*, b.BroadcastDate, m.Title as MovieTitle, th.TheaterId, s.SeatNumber, s.IsVip " +
                    "FROM tickets t " +
                    "JOIN broadcasts b ON t.BroadcastID = b.BroadcastID " +
                    "JOIN movies m ON b.MovieID = m.MovieID " +
                    "JOIN theaters th ON b.TheaterID = th.TheaterID " +
                    "JOIN seats s ON t.SeatID = s.SeatID " +
                    "WHERE t.UserID IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                // Find the corresponding broadcast
                Broadcast broadcast = null;
                for (Broadcast b : broadcastManager.broadcasts) {
                    if (b.movie.Title.equals(rs.getString("MovieTitle")) && 
                        b.Date.equals(rs.getDate("BroadcastDate").toLocalDate())) {
                        broadcast = b;
                        break;
                    }
                }

                if (broadcast != null) {
                    // Create seat object with proper SeatType
                    Seats seat = new Seats(rs.getInt("SeatNumber"), 
                        rs.getBoolean("IsVip") ? Seats.SeatType.VIP : Seats.SeatType.REGULAR);
                    
                    // Create ticket with proper TicketType based on SeatType
                    Ticket ticket = new Ticket(seat, 
                        seat.Type == Seats.SeatType.VIP ? Ticket.TicketType.VIP : Ticket.TicketType.Normal, 
                        broadcast);
                    
                    bookedTickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading tickets from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<Ticket> getTicketsByUserId(int userId) {
        ArrayList<Ticket> userTickets = new ArrayList<>();
        String sql = "SELECT t.*, b.BroadcastDate, m.Title as MovieTitle, th.TheaterName, s.SeatNumber, s.IsVip, t.TicketID, t.Price " +
                    "FROM tickets t " +
                    "JOIN broadcasts b ON t.BroadcastID = b.BroadcastID " +
                    "JOIN movies m ON b.MovieID = m.MovieID " +
                    "JOIN theaters th ON b.TheaterID = th.TheaterID " +
                    "JOIN seats s ON t.SeatID = s.SeatID " +
                    "WHERE t.UserID = ? " +
                    "ORDER BY b.BroadcastDate DESC";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Find the corresponding broadcast
                Broadcast broadcast = null;
                for (Broadcast b : broadcastManager.broadcasts) {
                    if (b.movie.Title.equals(rs.getString("MovieTitle")) && 
                        b.Date.equals(rs.getDate("BroadcastDate").toLocalDate())) {
                        broadcast = b;
                        break;
                    }
                }

                if (broadcast != null) {
                    // Create seat object with proper SeatType
                    Seats seat = new Seats(rs.getInt("SeatNumber"), 
                        rs.getBoolean("IsVip") ? Seats.SeatType.VIP : Seats.SeatType.REGULAR);
                    
                    // Create ticket with proper TicketType based on SeatType
                    Ticket ticket = new Ticket(seat, 
                        seat.Type == Seats.SeatType.VIP ? Ticket.TicketType.VIP : Ticket.TicketType.Normal, 
                        broadcast);
                    
                    // Set additional ticket information
                    ticket.TicketID = rs.getInt("TicketID");
                    ticket.Price = rs.getInt("Price");
                    
                    userTickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error getting user tickets: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return userTickets;
    }

    public void BookTicket(Client client, Broadcast broadcast, Ticket.TicketType type, Seats seat) {
        String sql = "INSERT INTO tickets (UserID, SeatID, BroadcastID, Price) " +
                    "SELECT ?, s.SeatID, ?, ? " +
                    "FROM seats s " +
                    "WHERE s.SeatNumber = ? AND s.TheaterID = ? AND s.IsVip = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Get broadcast ID
            int broadcastId = getBroadcastId(broadcast);
            if (broadcastId == -1) {
                JOptionPane.showMessageDialog(null, "Broadcast not found in database", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if seat is already booked for this broadcast
            if (!isSeatAvailable(broadcastId, seat.seatNumber, broadcast.Room.TheaterId)) {
                JOptionPane.showMessageDialog(null, "Seat is already booked", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate price based on ticket type
            int price = type == Ticket.TicketType.VIP ? 25 : 15;

            pstmt.setInt(1, client.Balance); // Using Balance as UserID temporarily
            pstmt.setInt(2, broadcastId);
            pstmt.setInt(3, price);
            pstmt.setInt(4, seat.seatNumber);
            pstmt.setInt(5, broadcast.Room.TheaterId);
            pstmt.setBoolean(6, type == Ticket.TicketType.VIP);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                Ticket ticket = new Ticket(seat, type, broadcast);
                ticket.Buyer = client;
                bookedTickets.add(ticket);
                
                // Update client balance
                clientManager.updateClientBalance(client.Balance, -price);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to book ticket", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error booking ticket: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getBroadcastId(Broadcast broadcast) {
        String sql = "SELECT BroadcastID FROM broadcasts WHERE MovieID = ? AND TheaterID = ? AND BroadcastDate = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, broadcast.movie.id);
            pstmt.setInt(2, broadcast.Room.TheaterId);
            pstmt.setDate(3, java.sql.Date.valueOf(broadcast.Date));
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("BroadcastID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getSeatId(Seats seat) {
        String sql = "SELECT SeatID FROM seats WHERE SeatNumber = ? AND IsVip = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seat.seatNumber);
            pstmt.setBoolean(2, seat.Type == Seats.SeatType.VIP);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("SeatID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void reloadBookedTicketsFromDatabase() {
        bookedTickets.clear();
        loadBookedTicketsFromDatabase();
    }

    public boolean isTicketAvailable(int broadcastId, int seatId) {
        String sql = "SELECT COUNT(*) FROM tickets WHERE BroadcastID = ? AND SeatID = ? AND UserID IS NULL";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, broadcastId);
            pstmt.setInt(2, seatId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isSeatAvailable(int broadcastId, int seatNumber, int theaterId) {
        String sql = "SELECT COUNT(*) FROM tickets t " +
                    "JOIN seats s ON t.SeatID = s.SeatID " +
                    "JOIN broadcasts b ON t.BroadcastID = b.BroadcastID " +
                    "WHERE b.BroadcastID = ? AND s.SeatNumber = ? AND s.TheaterID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, broadcastId);
            pstmt.setInt(2, seatNumber);
            pstmt.setInt(3, theaterId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Seat is available if no ticket exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getTheaterIdFromBroadcast(int broadcastId) {
        String sql = "SELECT TheaterID FROM broadcasts WHERE BroadcastID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, broadcastId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TheaterID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
