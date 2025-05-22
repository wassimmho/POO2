package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import controller.DatabaseConnection;

public class Theater {
    public int TheaterId;
    
    public int NormalCapacity;
    public int VipCapacity;

    public Boolean isAvailable;
    public int TotalPlaces;
    
    public int NormalReservedPlaces = 0;
    public int VipReservedPlaces = 0;

    public ArrayList<Seats> NormalSeats = new ArrayList<Seats>();
    public ArrayList<Seats> VipSeats = new ArrayList<Seats>();

    public Theater(int Id, int NormalCapacity, int VipCapacity, Boolean Available) {
        this.TheaterId = Id;
        this.NormalCapacity = NormalCapacity;
        this.VipCapacity = VipCapacity;
        this.isAvailable = Available;
        this.TotalPlaces = NormalCapacity + VipCapacity;
        loadSeatsFromDatabase();
    }

    public void loadSeatsFromDatabase() {
        String sql = "SELECT * FROM seats WHERE TheaterID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.TheaterId);
            ResultSet rs = pstmt.executeQuery();
            
            NormalSeats.clear();
            VipSeats.clear();
            
            while (rs.next()) {
                Seats seat = new Seats(rs.getInt("SeatNumber"), 
                    rs.getBoolean("IsVip") ? Seats.SeatType.VIP : Seats.SeatType.REGULAR);
                
                if (seat.Type == Seats.SeatType.REGULAR) {
                    NormalSeats.add(seat);
                } else {
                    VipSeats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading seats from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initialseat() {
        // First, clear existing seats from database
        String deleteSql = "DELETE FROM seats WHERE TheaterID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            
            pstmt.setInt(1, this.TheaterId);
            pstmt.executeUpdate();
            
            // Then insert new seats
            String insertSql = "INSERT INTO seats (TheaterID, SeatNumber, IsVip) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                
                // Insert normal seats
                for (int i = 0; i < NormalCapacity; i++) {
                    insertStmt.setInt(1, this.TheaterId);
                    insertStmt.setInt(2, i + 1);
                    insertStmt.setBoolean(3, false); // Regular seat
                    insertStmt.executeUpdate();
                }
                
                // Insert VIP seats
                for (int i = 0; i < VipCapacity; i++) {
                    insertStmt.setInt(1, this.TheaterId);
                    insertStmt.setInt(2, i + 1);
                    insertStmt.setBoolean(3, true); // VIP seat
                    insertStmt.executeUpdate();
                }
            }
            
            // Reload seats from database
            loadSeatsFromDatabase();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing seats in database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
