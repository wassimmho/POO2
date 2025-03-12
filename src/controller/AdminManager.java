package controller;

import model.*;
import java.sql.*;
import java.util.ArrayList;

public class AdminManager {

    public ArrayList<Admin> admins;

    public AdminManager() {
        admins = new ArrayList<>();
        //Create a default admin
        Admin admin1 = new Admin("fahd", "djedi", "fahd", "fahd@gmail.com", "yuji", Account.Status.Manager);

        addAdmin(admin1);
    }
    
    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public void removeAdmin(Admin admin) {
        admins.remove(admin);
    }
    
    public boolean isAdmin(String username, String password ){
        //Check if the admin credentials match with the stored admin credentials
        for(Admin admin : admins){
            if((admin.username.equals(username) || admin.Email.equals(username) || admin.PhoneNumber.equals(username))
                 && admin.password.equals(password)){
                return true;
            }
        }
        
        return false;
    }
    
    public Admin getAdmin(String username, String password){
        //Check if the client credentials match with the stored client credentials
        for(Admin admin : admins){
            if((admin.username.equals(username) || admin.Email.equals(username) || admin.PhoneNumber.equals(username))
                && (admin.password.equals(password))){
                return admin;
            }
        }
        //If client credentials don't match, return null
        return null;
    }
    
    public static void addAdmin(String name, String email, String password, boolean status) {
        String sql = "INSERT INTO admins (Name, Email, Password, Status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setBoolean(4, status); // Boolean is handled correctly
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Admin added successfully!");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


    public static void removeAdmin(int adminID) {
        String sql = "DELETE FROM admins WHERE AdminID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, adminID);
            
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("Admin deleted successfully!");
            } else {
                System.out.println("Admin not found. No deletion performed.");
            }    
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static int numberofadmins() {
        String sql = "SELECT COUNT(*) FROM admins";

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

    public static ResultSet findAdminssbyName(String name) {
        String sql = "SELECT * FROM admins WHERE Name = ? ";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public static ResultSet findAdminsbyEmail(String email) {
        String sql = "SELECT FROM admins Where Email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            return pstmt.executeQuery();
             } catch (SQLException e) {
            e.printStackTrace();
             } 
        return null;   
    }


    public static void UpdateAdminName(int Adminid, String name) {
        String sql = "UPDATE admins SET Name = ? WHERE AdminID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, Adminid);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("Admin name updated successfully!");
            } else {
            System.out.println("Admin not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        public static void updateAdminEmail(int Adminid, String email) {
        String sql = "UPDATE admins SET Email = ? WHERE AdminID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, Adminid);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("Admin email updated successfully!");
            } else {
            System.out.println("Admin not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        public static void updateAdminPassword(int adminid, String password) {
        String sql = "UPDATE admins SET password = ? WHERE AdminID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, password);
            pstmt.setInt(2, adminid);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("Admin password updated successfully!");
            } else {
            System.out.println("Admin not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
}
