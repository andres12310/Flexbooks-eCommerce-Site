/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf124.flexbooksrestservice.db;

import java.sql.*;

/**
 *
 * @author asus
 */
public class DatabaseUtils {
    
    public static ResultSet retrieveQueryResults(Connection connection, final String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("about to return resultSet!!!!!");
            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static boolean performDBUpdate(Connection connection, String sql, String... params) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);


            int i = 1;
            for (String param : params) {

                preparedStatement.setString(i++, param);

            }

            return preparedStatement.executeUpdate() > 0 ;

        } catch (SQLException e) {
            System.out.println("There was a SQL EXception and that's why it failed :(");
            return false;
        }
    }
}
