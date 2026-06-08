/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
//import com.typingspeed.dao.ResultDAO;
//import com.typingspeed.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author ASUS
 */


public class TypingTestFrame extends JFrame {
    private User currentUser;
    private ResultDAO resultDAO;
    
    private JTextArea lblParagraph;
    private JTextArea txtInput;
    private JLabel lblTimer;
    private JButton btnStart, btnBack;
    
    private Timer timer;
    private int timeLeft = 60;
    private boolean isRunning = false;
    
    private final String testParagraph = "The quick brown fox jumps over the lazy dog. Programming in Java is fun and rewarding. Secure databases are vital for modern software ecosystems.";

    public TypingTestFrame(User user) {
        this.currentUser = user;
        this.resultDAO = new ResultDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Typing Speed Test");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top UI panel (Timer & Controls)
        JPanel topPanel = new JPanel(new BorderLayout());
        lblTimer = new JLabel("Time Remaining: 60s", SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTimer, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Center UI panel (Text display and user entry area)
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblParagraph = new JTextArea(testParagraph);
        lblParagraph.setFont(new Font("Monospaced", Font.PLAIN, 15));
        lblParagraph.setLineWrap(true);
        lblParagraph.setWrapStyleWord(true);
        lblParagraph.setEditable(false);
        lblParagraph.setBackground(new Color(240, 240, 240));
        textPanel.add(new JScrollPane(lblParagraph));

        txtInput = new JTextArea();
        txtInput.setFont(new Font("Monospaced", Font.PLAIN, 15));
        txtInput.setLineWrap(true);
        txtInput.setWrapStyleWord(true);
        txtInput.setEnabled(false); // Enable only when started
        textPanel.add(new JScrollPane(txtInput));

        add(textPanel, BorderLayout.CENTER);

        // Bottom Operations Section
        JPanel bottomPanel = new JPanel(new FlowLayout());
        btnStart = new JButton("Start Test");
        btnBack = new JButton("Back to Dashboard");
        
        bottomPanel.add(btnStart);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        btnStart.addActionListener((ActionEvent e) -> startTest());
        btnBack.addActionListener((ActionEvent e) -> {
            if (timer != null) timer.stop();
            new DashboardFrame(currentUser).setVisible(true);
            this.dispose();
        });
    }

    private void startTest() {
        if (isRunning) return;
        
        isRunning = true;
        txtInput.setEnabled(true);
        txtInput.setText("");
        txtInput.requestFocus();
        btnStart.setEnabled(false);
        btnBack.setEnabled(false);
        timeLeft = 60;

        timer = new Timer(1000, (ActionEvent e) -> {
            timeLeft--;
            lblTimer.setText("Time Remaining: " + timeLeft + "s");
            
            if (timeLeft <= 0) {
                calculateAndSaveScore();
            }
        });
        timer.start();
    }

    private void calculateAndSaveScore() {
        timer.stop();
        isRunning = false;
        txtInput.setEnabled(false);
        btnStart.setEnabled(true);
        btnBack.setEnabled(true);

        String typed = txtInput.getText();
        String target = lblParagraph.getText();

        if (typed.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Test complete! You didn't type anything.", "Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Calculate WPM (5 characters = 1 standard word)
        int totalChars = typed.length();
        double wpm = (totalChars / 5.0); 

        // Compute Simple Accuracy
        String[] targetWords = target.split(" ");
        String[] typedWords = typed.split(" ");
        int correctWordsCount = 0;

        for (int i = 0; i < Math.min(targetWords.length, typedWords.length); i++) {
            if (targetWords[i].equals(typedWords[i])) {
                correctWordsCount++;
            }
        }
        
        double accuracy = ((double) correctWordsCount / Math.max(typedWords.length, 1)) * 100;

        // Round up to 2 decimals
        wpm = Math.round(wpm * 100.0) / 100.0;
        accuracy = Math.round(accuracy * 100.0) / 100.0;

        // Commit via Database Access Layer
        boolean saved = resultDAO.saveResult(currentUser.getId(), wpm, accuracy);

        if (saved) {
            JOptionPane.showMessageDialog(this, 
                "Test Finished!\nSpeed: " + wpm + " WPM\nAccuracy: " + accuracy + "% \nSaved to DB successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save results.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
