package controller;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Client;

public class ClientManager {


    public ArrayList<Client> clients;

    public ClientManager() {
        clients = new ArrayList<>();
        //Create a default client
        Client client1 = new Client("rayan", "mozali", 19, 
            20000, "rayanmozali@gmail.com", "00000", "rayan_mz", "1234");
        addClient(client1);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

        

    public boolean isClient(String username, String password ){
        //Check if the client credentials match with the stored client credentials
        for(Client client : clients){
            if((client.username.equals(username) || client.Email.equals(username) || client.PhoneNumber.equals(username))
                && (client.password.equals(password))){
                return true;
            }
        }
        
        //If client credentials don't match, add the client to the clients list
        return false;
    }

    
    public Client getClient(String username, String password){
        //Check if the client credentials match with the stored client credentials
        for(Client client : clients){
            if((client.username.equals(username) || client.Email.equals(username) || client.PhoneNumber.equals(username))
                && (client.password.equals(password))){
                return client;
            }
        }
        
        //If client credentials don't match, return null
        return null;
    }
    public static Boolean addClient(String username, String lname, String name, String email, String password, int age, int balance) {
        if (isDuplicate(username, email, password)) {
            JOptionPane.showMessageDialog(null, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO users (username, LastName, Name, Email, Password, Age, Balance) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, lname);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            pstmt.setString(5, password);
            pstmt.setInt(6, age);
            pstmt.setInt(7, balance);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("User added successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void removeClient(int userID) {
        String sql = "DELETE FROM users WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("User not found. No deletion performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDuplicate(String username, String email, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? OR Email = ? OR Password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if( rs.getInt(1) > 0){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
        }

            // Method to update only the username
    public static void updateClientUsername(int userID, String username) {
        String sql = "UPDATE users SET username = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, userID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Username updated successfully!");
            } else {
                System.out.println("User not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        // Method to update only the first name
    public static void updateClientFirstName(int userID, String firstName) {
            String sql = "UPDATE users SET Name = ? WHERE UserID = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, firstName);
                pstmt.setInt(2, userID);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("First name updated successfully!");
                } else {
                    System.out.println("User not found. No update performed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to update only the last name
        public static void updateClientLastName(int userID, String lastName) {
            String sql = "UPDATE users SET LastName = ? WHERE UserID = ?";

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, lastName);
                pstmt.setInt(2, userID);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Last name updated successfully!");
                } else {
                    System.out.println("User not found. No update performed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void updateClientEmail(int userID, String email) {
        String sql = "UPDATE users SET Email = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, userID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("User email updated successfully!");
            } else {
            System.out.println("User not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        public static void updateClientPassword(int userID, String password) {
        String sql = "UPDATE users SET Password = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, password);
            pstmt.setInt(2, userID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("User password updated successfully!");
            } else {
            System.out.println("User not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        public static void updateClientAge(int userID, int age) {
        String sql = "UPDATE users SET Age = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, age);
            pstmt.setInt(2, userID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("User age updated successfully!");
            } else {
            System.out.println("User not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        public static void updateClientBalance(int userID, int balance) {
        String sql = "UPDATE users SET Balance = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, balance);
            pstmt.setInt(2, userID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            System.out.println("User balance updated successfully!");
            } else {
            System.out.println("User not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        public static void updateClientPhoneNumber(int userID, String phoneNumber) {
            String sql = "UPDATE users SET PhoneNumber = ? WHERE UserID = ?";
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, phoneNumber);
                pstmt.setInt(2, userID);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void updateClientImage(int userID, String imgPath) {
            String sql = "UPDATE users SET imgpath = ? WHERE UserID = ?";
        
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
                pstmt.setString(1, imgPath);
                pstmt.setInt(2, userID);
        
                int rowsUpdated = pstmt.executeUpdate();
        
                if (rowsUpdated > 0) {
                    System.out.println("User image updated successfully!");
                } else {
                    System.out.println("User not found. No update performed.");
                }
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static boolean userExists(String userOrEmail, String password) {
            String sql = "SELECT COUNT(*) FROM users WHERE (username = ? OR Email = ?) AND Password = ?";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userOrEmail);
                pstmt.setString(2, userOrEmail);
                pstmt.setString(3, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }

        public static ResultSet findUsersbyusername(String username) {
            String sql = "SELECT * FROM users WHERE username = ? ";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, username);

                return pstmt.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        
        public static ResultSet findUsersbyEmail(String email) {
            String sql = "SELECT * FROM users WHERE Emaik = ? ";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, email);

                return pstmt.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static int numberofusers() {
            String sql = "SELECT COUNT(*) FROM users";
    
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

        public static int averagebalance() {
            String sql = "SELECT AVG(Balance) FROM users";
    
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


        
        public static int getuserid(String username){
            String sql = "SELECT UserID FROM users WHERE username = ?";
    
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
                pstmt.setString(1, username);
    
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return 0;

        }
        
        public static String getusersname(int userid) throws SQLException {
            String sql = "SELECT username FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
            }
            return null;
        }

        public static String getuseremail(int userid) throws SQLException {
            String sql = "SELECT Email FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
            }
            return null;
        }

        public static int getuserage(int userid) throws SQLException {
            String sql = "SELECT Age FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
            return 0;
        }

        public static int getuserbalance(int userid) throws SQLException {
            String sql = "SELECT Balance FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
            return 0;
        }

        public static String getuserphone(int userid) throws SQLException {
            String sql = "SELECT PhoneNumber FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
            }
            return null;
        }

        public static String getuserfirstname(int userid) throws SQLException {
            String sql = "SELECT Name FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
            }
            return null;
        }

        public static String getuserlastname(int userid) throws SQLException {
            String sql = "SELECT LastName FROM users WHERE UserID = ?";
            try(Connection conn = DatabaseConnection.connect();
                PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, userid);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
            }
            return null;
        }
        
        public static String getUserImagePath(int userID) throws SQLException {
            String sql = "SELECT imgpath FROM users WHERE UserID = ?";
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, userID);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
            return "img\\default.png"; // Return default image path if no image is found
        }

        public static boolean hasSufficientBalance(int userId, int amount) throws SQLException {
            String sql = "SELECT Balance FROM users WHERE UserID = ?";
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("Balance") >= amount;
                } else {
                    System.err.println("User ID: " + userId + " not found.");
                    return false;
                }
            }
        }

    public void reloadClientsFromDatabase() {
        clients.clear();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client(
                    rs.getString("Name"),
                    rs.getString("LastName"),
                    rs.getInt("Age"),
                    rs.getInt("Balance"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("username"),
                    rs.getString("Password")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
