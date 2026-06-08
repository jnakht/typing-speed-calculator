/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
//import com.typingspeed.dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author ASUS
 */


public class RegisterFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
    private UserDAO userDAO;

    public RegisterFrame() {
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Create Account");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("  Choose Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("  Choose Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnRegister = new JButton("Sign Up");
        btnBack = new JButton("Back to Login");

        add(btnRegister);
        add(btnBack);

        btnRegister.addActionListener((ActionEvent e) -> handleRegistration());
        btnBack.addActionListener((ActionEvent e) -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

    private void handleRegistration() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter values in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.");
            new LoginFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username might already be taken.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
