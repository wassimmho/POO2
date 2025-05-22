package controller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.*;

public class BroadcastManager {

    public MovieManager movieManager;
    public TheaterManager theaterManager;
    
    public ArrayList<Broadcast> broadcasts;


    public BroadcastManager() {
        broadcasts = new ArrayList<>();
        movieManager = new MovieManager();
        theaterManager = new TheaterManager();
        loadBroadcastsFromDatabase();
    }

    private void loadBroadcastsFromDatabase() {
        String sql = "SELECT * FROM broadcasts";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int movieId = rs.getInt("MovieID");
                int theaterId = rs.getInt("TheaterID");
                LocalDate date = rs.getDate("BroadcastDate").toLocalDate();
                String language = rs.getString("Language");

                // Find movie and theater objects from managers
                Movie movie = null;
                Theater theater = null;
                for (Movie m : MovieManager.movies) {
                    if (m.id == movieId) {
                        movie = m;
                        break;
                    }
                }
                for (Theater t : theaterManager.theaters) {
                    if (t.TheaterId == theaterId) {
                        theater = t;
                        break;
                    }
                }

                if (movie != null && theater != null) {
                    Broadcast broadcast = new Broadcast(movie, theater, date);
                    broadcasts.add(broadcast);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading broadcasts from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void addBroadcast(Broadcast broadcast) {
        String sql = "INSERT INTO broadcasts (MovieID, TheaterID, Language, BroadcastDate) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, broadcast.movie.id);
            pstmt.setInt(2, broadcast.Room.TheaterId);
            pstmt.setString(3, "VOSTFR"); // Default language
            pstmt.setDate(4, java.sql.Date.valueOf(broadcast.Date));

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                broadcasts.add(broadcast);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding broadcast to database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeBroadcast(Broadcast broadcast) {
        String sql = "DELETE FROM broadcasts WHERE MovieID = ? AND TheaterID = ? AND BroadcastDate = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, broadcast.movie.id);
            pstmt.setInt(2, broadcast.Room.TheaterId);
            pstmt.setDate(3, java.sql.Date.valueOf(broadcast.Date));

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                broadcasts.remove(broadcast);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing broadcast from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void DisplayBroadcast(){
        for(Broadcast broadcast : broadcasts){
            System.out.println("Movie: " + broadcast.movie.Title + " Theater: " + broadcast.Room.TheaterId + " Date: " + broadcast.Date);
        }
    }

    
    public void InitiliazeTicket(){
        for(Broadcast broadcast : broadcasts){
            for(Theater Room : theaterManager.theaters){
                for(int i = 0; i<Room.NormalSeats.size(); i++){
                    Ticket ticket = new Ticket(Room.NormalSeats.get(i), Ticket.TicketType.Normal, broadcast);
                    broadcast.tickets.add(ticket);
                }
                for(int i = 0; i<Room.NormalSeats.size(); i++){
                    Ticket ticket = new Ticket(Room.VipSeats.get(i), Ticket.TicketType.VIP, broadcast);
                    broadcast.tickets.add(ticket);
                }
            }
        }
        
    }
    
    public void DisplayTickets(Broadcast broadcast){
        for (Ticket ticket : broadcast.tickets) {
            System.out.println("---------------------------------");
            System.out.println("Ticket ID: " + ticket.Seat.seatNumber);
            System.out.println("Ticket Type: " + ticket.Type);
            System.out.println("movie : " + ticket.broadcast.movie.Title);
            System.out.println("Theater ID: " + ticket.broadcast.Room.TheaterId);
            System.out.println("Date: " + ticket.broadcast.Date);
            System.out.println("---------------------------------");
        }
    }

    public static void addBroadcast(int MovieID, int TheaterID, String Language, Date BroadcastDate) {
        String sql = "INSERT INTO broadcasts (MovieID, TheaterID, Language, BroadcastDate) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, MovieID);
            pstmt.setInt(2, TheaterID);
            pstmt.setString(3, Language);
            pstmt.setDate(4, BroadcastDate);
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Broadcast added successfully!");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateBroadcastTheater(int BroadcastId, int TheaterID) {
        String sql = "UPDATE broadcasts SET TheaterID = ? WHERE BroadcastID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, TheaterID);
            pstmt.setInt(2, BroadcastId);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("Theater updated successfully!");
            } else {
            System.out.println("Braodcast not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBroadcastmovie(int BroadcastId, int MovieID) {
        String sql = "UPDATE broadcasts SET MovieID = ? WHERE BroadcastID = ?";
    
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, MovieID);
            pstmt.setInt(2, BroadcastId);
    
            int rowsUpdated = pstmt.executeUpdate();
    
            if (rowsUpdated > 0) {
            System.out.println("Theater updated successfully!");
            } else {
            System.out.println("Braodcast not found. No update performed.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateBroadcastDate(int BroadcastId, LocalDate Date) {
        String sql = "UPDATE broadcasts SET BroadcastDate = ? WHERE BroadcastID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setDate(1, java.sql.Date.valueOf(Date));
            pstmt.setInt(2, BroadcastId);
        
            int rowsUpdated = pstmt.executeUpdate();
        
            if (rowsUpdated > 0) {
                System.out.println("Theater updated successfully!");
            } else {
                System.out.println("Braodcast not found. No update performed.");
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBroadcast(int BroadcastId) {
        String sql = "DELETE FROM broadcasts WHERE BroadcastID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, BroadcastId);
        
            int rowsDeleted = pstmt.executeUpdate();
        
            if (rowsDeleted > 0) {
                System.out.println("Broadcast deleted successfully!");
            } else {
                System.out.println("Broadcast not found. No deletion performed.");
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reloadBroadcastsFromDatabase() {
        broadcasts.clear();
        loadBroadcastsFromDatabase();
    }
    
    
    public Broadcast getBroadcastByMovieAndDate(Movie movie, LocalDate date) {
        for (Broadcast broadcast : broadcasts) {
            if (broadcast.movie.equals(movie) && broadcast.Date.equals(date)) {
                return broadcast;
            }
        }
        return null; // Return null if no broadcast is found for the given movie
    }

    public Theater getTheaterByMovieAndDate(Movie movie, LocalDate date) {
        for (Broadcast broadcast : broadcasts) {
            if (broadcast.movie.equals(movie) && broadcast.Date.equals(date)) {
                return broadcast.Room;
            }
        }
        return null; // Return null if no broadcast is found for the given movie
    }

}            
