package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.*;

public class TheaterManager {

    public ArrayList<Theater> theaters;

    public TheaterManager() {
        this.theaters = new ArrayList<>();
        loadTheatersFromDatabase();
    }

    private void loadTheatersFromDatabase() {
        String sql = "SELECT * FROM theaters";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Theater theater = new Theater(
                    rs.getInt("TheaterID"),
                    rs.getInt("NormalSeats"),
                    rs.getInt("VIPSeats"),
                    true // Default isAvailable value
                );
                theaters.add(theater);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading theaters from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void AddTheater(Theater theater) {
        String sql = "INSERT INTO theaters (TheaterName, NormalSeats, VIPSeats) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "Room " + (theaters.size() + 1));
            pstmt.setInt(2, theater.NormalCapacity);
            pstmt.setInt(3, theater.VipCapacity);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                // Get the generated TheaterID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        theater.TheaterId = generatedKeys.getInt(1);
                    }
                }
                theaters.add(theater);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding theater to database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setTheaterUnAvailable(int theaterId) {
        // Only update the in-memory object since Available is not in database
        if (theaterId >= 0 && theaterId < theaters.size()) {
            theaters.get(theaterId).isAvailable = false;
        }
    }

    public void setTheaterAvailable(int theaterId) {
        // Only update the in-memory object since Available is not in database
        if (theaterId >= 0 && theaterId < theaters.size()) {
            theaters.get(theaterId).isAvailable = true;
        }
    }

    public void DisplayTheater(){
        for(Theater theater : theaters){
            System.out.println("---------------------------------");
            System.out.println("Theater ID: " + theater.TheaterId);
            System.out.println("Total Places: " + theater.TotalPlaces);
            System.out.println("Normal Capacity: " + theater.NormalCapacity);
            System.out.println("Vip Capacity: " + theater.VipCapacity);
            System.out.println("Availability: " + theater.isAvailable);
            System.out.println("Normal Reserved Places: " + theater.NormalReservedPlaces);
            System.out.println("Vip Reserved Places: " + theater.VipReservedPlaces);
            System.out.println("---------------------------------");
        }
    }

    public void reloadTheatersFromDatabase() {
        theaters.clear();
        loadTheatersFromDatabase();
    }

    public static int numberoftheaters() {
            String sql = "SELECT COUNT(*) FROM theaters";
    
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return 0;
        }

                // Add a new theater to the database
        public static boolean addTheater(String theaterName, int normalSeats, int vipSeats) {
            String sql = "INSERT INTO theaters (TheaterName, NormalSeats, VIPSeats) VALUES (?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, theaterName);
                pstmt.setInt(2, normalSeats);
                pstmt.setInt(3, vipSeats);

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Theater added successfully!");
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }

        // Remove a theater from the database
        public static void removeTheater(int theaterID) {
            String sql = "DELETE FROM theaters WHERE TheaterID = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, theaterID);

                int rowsDeleted = pstmt.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Theater deleted successfully!");
                } else {
                    System.out.println("Theater not found. No deletion performed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Update the name of a theater
        public static void updateTheaterName(int theaterID, String theaterName) {
            String sql = "UPDATE theaters SET TheaterName = ? WHERE TheaterID = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, theaterName);
                pstmt.setInt(2, theaterID);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Theater name updated successfully!");
                } else {
                    System.out.println("Theater not found. No update performed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Update the normal seats of a theater
        public static void updateNormalSeats(int theaterID, int normalSeats) {
            String sql = "UPDATE theaters SET NormalSeats = ? WHERE TheaterID = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, normalSeats);
                pstmt.setInt(2, theaterID);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Normal seats updated successfully!");
                } else {
                    System.out.println("Theater not found. No update performed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Update the VIP seats of a theater
        public static void updateVIPSeats(int theaterID, int vipSeats) {
            String sql = "UPDATE theaters SET VIPSeats = ? WHERE TheaterID = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, vipSeats);
                pstmt.setInt(2, theaterID);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("VIP seats updated successfully!");
                } else {
                    System.out.println("Theater not found. No update performed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Check if a theater exists by name
        public static boolean theaterExists(String theaterName) {
            String sql = "SELECT COUNT(*) FROM theaters WHERE TheaterName = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, theaterName);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }

        // Get the total number of theaters
        public static int numberOfTheaters() {
            String sql = "SELECT COUNT(*) FROM theaters";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }

        // Get the total number of normal seats across all theaters
        public static int totalNormalSeats() {
            String sql = "SELECT SUM(NormalSeats) FROM theaters";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }

        // Get the total number of VIP seats across all theaters
        public static int totalVIPSeats() {
            String sql = "SELECT SUM(VIPSeats) FROM theaters";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }

        // Get the names of total available theaters by date 
        public static ArrayList<String> getAvailableTheatersByDate(LocalDate date) {
            String sql = "SELECT TheaterName FROM theaters WHERE Available = 1 AND Date = ?";
            ArrayList<String> availableTheaters = new ArrayList<>();

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setDate(1, java.sql.Date.valueOf(date));

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    availableTheaters.add(rs.getString("TheaterName"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return availableTheaters;
        }

        // Get avaible date by theater name
        public static ArrayList<LocalDate> getAvailableDatesByTheaterName(String theaterName) {
            String sql = "SELECT Date FROM theaters WHERE TheaterName = ? AND Available = 1";
            ArrayList<LocalDate> availableDates = new ArrayList<>();

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, theaterName);

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    availableDates.add(rs.getDate("Date").toLocalDate());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return availableDates;
        }

        //get all theaters names
        public static ArrayList<String> getAllTheaterNames() {
            String sql = "SELECT TheaterName FROM theaters";
            ArrayList<String> theaterNames = new ArrayList<>();

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    theaterNames.add(rs.getString("TheaterName"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return theaterNames;
        }

        //get theater id by name
        public static int getTheaterIdByName(String theaterName) {
            String sql = "SELECT TheaterID FROM theaters WHERE TheaterName = ?";
            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, theaterName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("TheaterID");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1; // Not found
        }
}

