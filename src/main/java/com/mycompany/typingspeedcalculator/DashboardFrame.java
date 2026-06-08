/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
//import User;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ASUS
 */


public class DashboardFrame extends JFrame {
    private User currentUser;
    private JLabel lblWelcome;
    private JButton btnStartTest, btnHistory, btnLeaderboard, btnLogout;

    public DashboardFrame(User user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Typing Dashboard");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        lblWelcome = new JLabel("Welcome, " + currentUser.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblWelcome, BorderLayout.NORTH);

        JPanel panelButtons = new JPanel(new GridLayout(4, 1, 10, 10));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        btnStartTest = new JButton("Start Typing Test");
        btnHistory = new JButton("View Personal History");
        btnLeaderboard = new JButton("Global Leaderboard");
        btnLogout = new JButton("Logout");

        panelButtons.add(btnStartTest);
        panelButtons.add(btnHistory);
        panelButtons.add(btnLeaderboard);
        panelButtons.add(btnLogout);

        add(panelButtons, BorderLayout.CENTER);

        // Routing Actions
        btnStartTest.addActionListener(e -> {
            new TypingTestFrame(currentUser).setVisible(true);
            this.dispose();
        });
        
        btnHistory.addActionListener(e -> {
            new HistoryFrame(currentUser).setVisible(true);
            this.dispose();
        });

        btnLeaderboard.addActionListener(e -> {
            new LeaderboardFrame(currentUser).setVisible(true);
            this.dispose();
        });

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }
}
