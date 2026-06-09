/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author ASUS
 */
public class DashboardFrame extends JFrame {
    private User currentUser;
    
    // UI Components
    private JLabel lblWelcome;
    private JLabel lblSubtitle;
    private JButton btnStartTest, btnHistory, btnLeaderboard, btnLogout;

    // Premium Web UI Design Palette
    private final Color CANVAS_BG = new Color(250, 250, 252);
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_BLUE = new Color(0, 102, 255);
    private final Color DARK_TEXT = new Color(30, 30, 35);
    private final Color MUTED_TEXT = new Color(120, 120, 128);
    private final Color BORDER_COLOR = new Color(235, 235, 240);

    public DashboardFrame(User user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Typing Dashboard");
        setSize(850, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Apply clean web container background color
        getContentPane().setBackground(CANVAS_BG);
        setLayout(new BorderLayout(0, 0));

        // 1. LEFT SIDEBAR PANEL (Profile, Branding, and Logout)
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(CARD_BG);
        sidebarPanel.setPreferredSize(new Dimension(260, 500));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, BORDER_COLOR));
        sidebarPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 25, 10, 25);

        // Header Application Branding Title - Removed the broken bolt icon
        JLabel lblLogo = new JLabel("TYPE SPEED");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(ACCENT_BLUE);
        gbc.gridy = 0;
        sidebarPanel.add(lblLogo, gbc);

        // Thin Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 25, 25, 25);
        sidebarPanel.add(separator, gbc);

        // Welcome Greeting
        lblWelcome = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(DARK_TEXT);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 25, 2, 25);
        sidebarPanel.add(lblWelcome, gbc);

        lblSubtitle = new JLabel("Ready to break records?");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(MUTED_TEXT);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 25, 20, 25);
        sidebarPanel.add(lblSubtitle, gbc);

        // Box Spacer pushing the exit path to the bottom edge of the container
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        sidebarPanel.add(Box.createGlue(), gbc);

        // Logout Button Configuration
        btnLogout = createFlatButton("Logout", Color.WHITE, new Color(255, 77, 77));
        btnLogout.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 200, 200), 1),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        gbc.gridy = 5;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(20, 25, 30, 25);
        sidebarPanel.add(btnLogout, gbc);

        add(sidebarPanel, BorderLayout.WEST);

        // 2. MAIN HUB CONTENT AREA (Action Buttons Grid Layout)
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainContentPanel.setLayout(new GridLayout(1, 2, 25, 25)); 

        // Nested column layout panel for secondary operations
        JPanel secondaryCardsGrid = new JPanel(new GridLayout(2, 1, 25, 25));
        secondaryCardsGrid.setOpaque(false);

        // Initialize Primary Platform Action Cards - Removed all emoji characters completely
        btnStartTest = createFlatButton("Start Typing Test", ACCENT_BLUE, Color.WHITE);
        btnStartTest.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        btnHistory = createFlatButton("View Personal History", CARD_BG, DARK_TEXT);
        btnHistory.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        btnLeaderboard = createFlatButton("Global Leaderboard", CARD_BG, DARK_TEXT);
        btnLeaderboard.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Add sub-cards to right column stack
        secondaryCardsGrid.add(btnHistory);
        secondaryCardsGrid.add(btnLeaderboard);

        // Left tile is double-sized primary action; right side contains secondary dashboard logs
        mainContentPanel.add(btnStartTest); 
        mainContentPanel.add(secondaryCardsGrid);

        add(mainContentPanel, BorderLayout.CENTER);

        // 3. FRAME ROUTING ACTIONS
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

    /**
     * Factory Helper Method to generate modern flat clickable canvas structures.
     * Incorporates real-time background color changes on hover events.
     */
    private JButton createFlatButton(String text, Color backgroundColor, Color foregroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Remove default bubble borders with a sleek solid line bounding box frame
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Web-like responsive hover actions listener matrix
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (backgroundColor == CARD_BG) {
                    button.setBackground(new Color(242, 242, 247)); // Soft gray hover effect
                } else if (backgroundColor == ACCENT_BLUE) {
                    button.setBackground(new Color(0, 90, 230)); // Deep blue hover effect
                } else if (text.equals("Logout")) {
                    button.setBackground(new Color(255, 242, 242)); // Pale red highlight focus
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }
}