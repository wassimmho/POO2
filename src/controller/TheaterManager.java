package controller;

import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
