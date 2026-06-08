/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;

/**
 *
 * @author ASUS
 */



import java.sql.Connection;
import java.sql.DriverManager;






public class DBConnection {

    public static Connection getConnection() {

        try {

            String url =
                    "jdbc:mysql://localhost:3306/typing_speed_db";

            String username = "root";

            String password = "";

            Connection conn =
                    DriverManager.getConnection(
                            url,
                            username,
                            password
                    );

            System.out.println("Database Connected!");

            return conn;

        } catch (Exception e) {

            System.out.println(
                    "Connection Failed!"
            );

            e.printStackTrace();

            return null;
        }
    }
}
