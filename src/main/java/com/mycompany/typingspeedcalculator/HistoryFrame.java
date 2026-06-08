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


public class HistoryFrame extends JFrame {
    private User currentUser;
    private ResultDAO resultDAO;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnBack;

    public HistoryFrame(User user) {
        this.currentUser = user;
        this.resultDAO = new ResultDAO();
        initComponents();
        loadHistoryData();
    }

    private void initComponents() {
        setTitle("Your Typing Performance Logs");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Your Test History", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Test Date & Time", "Speed (WPM)", "Accuracy (%)"};
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

    private void loadHistoryData() {
        List<Result> history = resultDAO.getUserHistory(currentUser.getId());
        for (Result r : history) {
            Object[] row = { r.getTestDate(), r.getWpm(), r.getAccuracy() };
            model.addRow(row);
        }
    }
}
