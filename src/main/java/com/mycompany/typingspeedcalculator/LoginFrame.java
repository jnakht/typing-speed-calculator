/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;
//import com.typingspeed.dao.UserDAO;
//import com.typingspeed.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author ASUS
 */

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Typing Speed Calculator - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("  Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("  Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register New Account");

        add(btnLogin);
        add(btnRegister);

        btnLogin.addActionListener((ActionEvent e) -> handleLogin());
        btnRegister.addActionListener((ActionEvent e) -> {
            new RegisterFrame().setVisible(true);
            this.dispose();
        });
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userDAO.loginUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + user.getUsername());
            new DashboardFrame(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
