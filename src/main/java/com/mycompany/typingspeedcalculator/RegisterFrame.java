/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;

//import com.typingspeed.dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author ASUS
 */
public class RegisterFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
    private UserDAO userDAO;

    // Premium Web UI Design Palette matching Login & Dashboard frames
    private final Color CANVAS_BG = new Color(250, 250, 252);
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_BLUE = new Color(0, 102, 255);
    private final Color DARK_TEXT = new Color(30, 30, 35);
    private final Color MUTED_TEXT = new Color(120, 120, 128);
    private final Color BORDER_COLOR = new Color(235, 235, 240);

    public RegisterFrame() {
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("TypeSpeed - Create Account");
        setSize(450, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(CANVAS_BG);
        setLayout(new GridBagLayout()); // Centering the floating card layout

        // 1. MAIN CARD PANEL CONTAINER
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_BG);
        cardPanel.setPreferredSize(new Dimension(380, 420));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // 2. HEADER SECTION
        JLabel lblTitle = new JLabel("Create Account");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(DARK_TEXT);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Join and track your typing milestones");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(MUTED_TEXT);
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(lblTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        cardPanel.add(lblSubtitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        // 3. INPUT FORM FIELDS
        // Username Area
        JLabel lblUser = new JLabel("Choose Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(DARK_TEXT);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtUsername = new JTextField();
        styleInputField(txtUsername);

        cardPanel.add(lblUser);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        cardPanel.add(txtUsername);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

        // Password Area
        JLabel lblPass = new JLabel("Choose Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPass.setForeground(DARK_TEXT);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtPassword = new JPasswordField();
        styleInputField(txtPassword);

        cardPanel.add(lblPass);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        cardPanel.add(txtPassword);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // 4. ACTION INTERFACE BUTTONS
        // Primary Action Button (Sign Up)
        btnRegister = new JButton("Sign Up");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnRegister.setBackground(ACCENT_BLUE);
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setMaximumSize(new Dimension(Short.MAX_VALUE, 45)); 
        btnRegister.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // Flat Redirect Button (Back to Login)
        btnBack = new JButton("Already have an account? Log In");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBack.setForeground(ACCENT_BLUE);
        btnBack.setBackground(CARD_BG);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(null); 
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(btnRegister);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(btnBack);

        add(cardPanel);

        // Hover animation logic for primary button
        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegister.setBackground(new Color(0, 90, 230)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRegister.setBackground(ACCENT_BLUE);
            }
        });

        // Event Actions (With inferred types to avoid package compilation errors)
        btnRegister.addActionListener(e -> handleRegistration());
        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void styleInputField(JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 215), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10) 
        ));
    }

    private void handleRegistration() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            WebStyledNotificationDialog validationError = new WebStyledNotificationDialog(this, "Validation Error", "Please enter values in all fields.", true);
            validationError.setVisible(true);
            return;
        }

        if (userDAO.registerUser(username, password)) {
            WebStyledNotificationDialog successPopup = new WebStyledNotificationDialog(this, "Success", "Registration Successful! You can now log in.", false);
            successPopup.setVisible(true);
            
            new LoginFrame().setVisible(true);
            this.dispose();
        } else {
            WebStyledNotificationDialog failPopup = new WebStyledNotificationDialog(this, "Registration Failed", "Username might already be taken.", true);
            failPopup.setVisible(true);
        }
    }

    /**
     * PREMIUM WEB CUSTOM NOTIFICATION DIALOG COMPONENT
     * Embedded inner class to display flat custom popups over the register card panel
     */
    private class WebStyledNotificationDialog extends JDialog {
        public WebStyledNotificationDialog(JFrame parent, String title, String message, boolean isError) {
            super(parent, title, true);
            setUndecorated(true); 
            setSize(400, 180);
            setLocationRelativeTo(parent);

            JPanel rootPanel = new JPanel();
            rootPanel.setBackground(Color.WHITE);
            rootPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 2));
            rootPanel.setLayout(new BorderLayout(15, 15));

            JPanel contentPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 5, 25));

            JLabel lblStatus = new JLabel(title);
            lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblStatus.setForeground(isError ? new Color(255, 77, 77) : ACCENT_BLUE);

            JLabel lblMessage = new JLabel(message);
            lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            lblMessage.setForeground(DARK_TEXT);

            contentPanel.add(lblStatus);
            contentPanel.add(lblMessage);
            rootPanel.add(contentPanel, BorderLayout.CENTER);

            JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
            footerPanel.setOpaque(false);

            JButton btnOk = new JButton("Continue");
            btnOk.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnOk.setBackground(isError ? new Color(245, 245, 247) : ACCENT_BLUE);
            btnOk.setForeground(isError ? DARK_TEXT : Color.WHITE);
            btnOk.setFocusPainted(false);
            btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnOk.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isError ? new Color(210, 210, 215) : new Color(0, 90, 230), 1),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
            ));

            btnOk.addActionListener(e -> dispose());
            footerPanel.add(btnOk);
            rootPanel.add(footerPanel, BorderLayout.SOUTH);

            add(rootPanel);
        }
    }
}