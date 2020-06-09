/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf124.flexbooksrestservice.db;

/**
 *
 * @author asus
 */

import java.sql.*;


public class DatabaseConnector {


    private DatabaseConnector() {

    }

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/flexbooksdb?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
