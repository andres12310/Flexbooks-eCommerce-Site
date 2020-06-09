/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf124.flexbooksrestservice.service;

import java.sql.Connection;
import com.inf124.flexbooksrestservice.db.DatabaseConnector;
import com.inf124.flexbooksrestservice.db.DatabaseUtils;
import com.inf124.flexbooksrestservice.model.Order;

/**
 *
 * @author asus
 */
public class OrderService {
    
    public static boolean addOrder(Order order) {

        String sql = "INSERT INTO Orders (isbn, quantity, firstName, lastName, email, phoneNumber, address, apt, city, state, zipCode, shippingSpeed, creditCard, cardExpiration, cvv)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnector.getConnection();
        return DatabaseUtils.performDBUpdate(connection, sql, order.getISBN(), order.getQuantity(), order.getFirstName(), order.getLastName(), order.getEmail(), order.getPhoneNumber(), order.getAddress(), order.getApt(), order.getCity(), order.getState(), order.getZipCode(), order.getShippingSpeed(), order.getCreditCard(), order.getCardExpiration(), order.getCVV());

        
        
    }
}
