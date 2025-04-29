package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingManager {

    public BookingManager() {
    }

    public int getTicketPrice(Connection connection, int broadcastId, boolean isVIP) throws SQLException {
        return isVIP ? 25 : 15;
    }

    public int generateNewTicket(Connection connection, int userId, int broadcastId, boolean isVIP) throws SQLException {
        int seatId = findAvailableSeat(connection, broadcastId, isVIP);

        if (seatId == -1) {
            System.out.println("No " + (isVIP ? "VIP" : "Normal") + " seat available for Broadcast ID: " + broadcastId);
            return -1;
        }

        int ticketPrice = getTicketPrice(connection, broadcastId, isVIP);
        if (ticketPrice == -1) {
            return -1;
        }

        if (!ClientManager.hasSufficientBalance(userId, ticketPrice)) {
            System.out.println("User ID: " + userId + " does not have sufficient balance to purchase the ticket.");
            return -1;
        }

        String insertTicketSQL = "INSERT INTO tickets (UserID, SeatID, BroadcastID, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertTicketSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, seatId);
            pstmt.setInt(3, broadcastId);
            pstmt.setInt(4, ticketPrice);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int ticketId = generatedKeys.getInt(1);
                        ClientManager.updateClientBalance(userId, -ticketPrice);
                        return ticketId;
                    } else {
                        System.err.println("Failed to retrieve TicketID.");
                        return -1;
                    }
                }
            } else {
                System.err.println("Failed to create new ticket.");
                return -1;
            }
        }
    }

    private int findAvailableSeat(Connection connection, int broadcastId, boolean isVIP) throws SQLException {
        String getTheaterIdSQL = "SELECT TheaterID FROM broadcasts WHERE BroadcastID = ?";
        int theaterId = -1;
        try (PreparedStatement pstmt = connection.prepareStatement(getTheaterIdSQL)) {
            pstmt.setInt(1, broadcastId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                theaterId = rs.getInt("TheaterID");
            } else {
                System.out.println("Broadcast ID: " + broadcastId + " not found.");
                return -1;
            }
        }

        String findSeatSQL = "SELECT s.SeatID FROM seats s " +
                             "WHERE s.TheaterID = ? AND s.IsVIP = ? " +
                             "AND s.SeatID NOT IN (SELECT t.SeatID FROM tickets t WHERE t.BroadcastID = ?) " +
                             "LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(findSeatSQL)) {
            pstmt.setInt(1, theaterId);
            pstmt.setBoolean(2, isVIP);
            pstmt.setInt(3, broadcastId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("SeatID");
            } else {
                return -1;
            }
        }
    }
}
