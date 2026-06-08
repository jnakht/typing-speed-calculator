/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class Result {
    private int id;
    private int userId;
    private String username; // For leaderboard display convenience
    private double wpm;
    private double accuracy;
    private Timestamp testDate;

    // Constructor for saving/retrieving
    public Result(int id, int userId, String username, double wpm, double accuracy, Timestamp testDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.testDate = testDate;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public double getWpm() { return wpm; }
    public double getAccuracy() { return accuracy; }
    public Timestamp getTestDate() { return testDate; }
}