package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;

public class TheaterManager {

    public ArrayList<Theater> theaters;

    public TheaterManager() {

        this.theaters = new ArrayList<>();

        Theater Room1 = new Theater(0, 200, 40, 1, true);
        Theater Room2 = new Theater(1, 300, 60, 2, true);
        Theater Room3 = new Theater(2, 400, 80, 3, true);
        Theater Room4 = new Theater(3, 200, 40, 4, true);
        Theater Room5 = new Theater(4, 300, 60, 5, true);
        Theater Room6 = new Theater(5, 200, 40, 6, true);
        Theater Room7 = new Theater(6, 200, 40, 7, true);

        AddTheater(Room1);
        AddTheater(Room2);
        AddTheater(Room3);
        AddTheater(Room4);
        AddTheater(Room5);
        AddTheater(Room6);
        AddTheater(Room7);
    }

    public void AddTheater(Theater theater) {
        theaters.add(theater);
    }

    public void setTheaterUnAvailable(int theaterId) {
        theaters.get(theaterId).isAvailable = false;
    }

    public void setTheaterAvailable(int theaterId) {
        theaters.get(theaterId).isAvailable = true;
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
}
