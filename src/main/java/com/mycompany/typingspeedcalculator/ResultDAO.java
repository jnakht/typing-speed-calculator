/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
//import com.typingspeed.connection.DBConnection;
//import com.typingspeed.model.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */

public class ResultDAO {

    public boolean saveResult(int userId, double wpm, double accuracy) {
        String query = "INSERT INTO results (user_id, wpm, accuracy) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            stmt.setDouble(2, wpm);
            stmt.setDouble(3, accuracy);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Result> getUserHistory(int userId) {
        List<Result> list = new ArrayList<>();
        String query = "SELECT r.*, u.username FROM results r JOIN users u ON r.user_id = u.id WHERE r.user_id = ? ORDER BY r.test_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Result(
                        rs.getInt("id"), rs.getInt("user_id"), rs.getString("username"),
                        rs.getDouble("wpm"), rs.getDouble("accuracy"), rs.getTimestamp("test_date")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Result> getLeaderboard() {
        List<Result> list = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, r.wpm, r.accuracy, r.test_date, u.username " +
                       "FROM results r JOIN users u ON r.user_id = u.id " +
                       "ORDER BY r.wpm DESC LIMIT 10";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Result(
                    rs.getInt("id"), rs.getInt("user_id"), rs.getString("username"),
                    rs.getDouble("wpm"), rs.getDouble("accuracy"), rs.getTimestamp("test_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
