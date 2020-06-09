/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf124.flexbooksrestservice.service;

/**
 *
 * @author asus
 */

import com.inf124.flexbooksrestservice.db.DatabaseConnector;
import com.inf124.flexbooksrestservice.db.DatabaseUtils;
import com.inf124.flexbooksrestservice.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {


    private final static String ALL_BOOKS_QUERY = "SELECT * FROM textbooks";

    public static Book getBookById(int id) {
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_BOOKS_QUERY + " WHERE ID = " + id);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Book book = new Book();


                    book.setId(resultSet.getInt("ID"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setPrice(resultSet.getString("price"));
                    book.setEdition(resultSet.getInt("edition"));
                    book.setType(resultSet.getString("type"));
                    book.setPageCount(resultSet.getString("pageCount"));
                    book.setPublisher(resultSet.getString("publisher"));
                    book.setLanguage(resultSet.getString("language"));
                    book.setIsbn10(resultSet.getString("ISBN10"));
                    book.setIsbn13(resultSet.getString("ISBN13"));
                    book.setProductDimensions(resultSet.getString("productDimensions"));
                    book.setShippingWeight(resultSet.getString("shippingWeight"));
                    book.setImage(resultSet.getString("image"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setCategory(resultSet.getString("category"));

                    return book;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {

                    // We will always close the connection once we are done interacting with the Database.
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;


    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();

        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_BOOKS_QUERY);
        System.out.println("This is the result set: " + resultSet);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Book book = new Book();

                    book.setId(resultSet.getInt("ID"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setPrice(resultSet.getString("price"));
                    book.setEdition(resultSet.getInt("edition"));
                    book.setType(resultSet.getString("type"));
                    book.setPageCount(resultSet.getString("pageCount"));
                    book.setPublisher(resultSet.getString("publisher"));
                    book.setLanguage(resultSet.getString("language"));
                    book.setIsbn10(resultSet.getString("ISBN10"));
                    book.setIsbn13(resultSet.getString("ISBN13"));
                    book.setProductDimensions(resultSet.getString("productDimensions"));
                    book.setShippingWeight(resultSet.getString("shippingWeight"));
                    book.setImage(resultSet.getString("image"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setCategory(resultSet.getString("category"));

                    books.add(book);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return books;
    }
}
