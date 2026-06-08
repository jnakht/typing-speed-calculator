/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
//import com.typingspeed.dao.ResultDAO;
//import com.typingspeed.model.Result;
//import com.typingspeed.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author ASUS
 */


public class LeaderboardFrame extends JFrame {
    private User currentUser;
    private ResultDAO resultDAO;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnBack;

    public LeaderboardFrame(User user) {
        this.currentUser = user;
        this.resultDAO = new ResultDAO();
        initComponents();
        loadLeaderboardData();
    }

    private void initComponents() {
        setTitle("Global Typing Rankings");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Top 10 Typing Champions", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Rank", "Competitor", "High Speed (WPM)", "Accuracy", "Achieved At"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnBack = new JButton("Return to Dashboard");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            this.dispose();
        });
    }

    private void loadLeaderboardData() {
        List<Result> topRecords = resultDAO.getLeaderboard();
        int rank = 1;
        for (Result r : topRecords) {
            Object[] row = { rank++, r.getUsername(), r.getWpm(), r.getAccuracy() + "%", r.getTestDate() };
            model.addRow(row);
        }
    }
}
