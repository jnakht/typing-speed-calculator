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




public class Main {
    public static void main(String[] args) {
        Connection conn =
                DBConnection.getConnection();

        if (conn != null) {

            System.out.println(
                    "Connection Successful!"
            );

        } else {

            System.out.println(
                    "Connection Failed!"
            );
        }
    }
}
