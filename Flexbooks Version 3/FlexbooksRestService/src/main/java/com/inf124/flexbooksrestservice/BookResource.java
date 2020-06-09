/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf124.flexbooksrestservice;

/**
 *
 * @author asus
 */

import com.inf124.flexbooksrestservice.model.Book;
import com.inf124.flexbooksrestservice.service.BookService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
public class BookResource {


    //This method represents an endpoint with the URL /todos/{id} and a GET request ( Note that {id} is a placeholder for a path parameter)
    @Path("{id}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getBookById(@PathParam("id") int id/* The {id} placeholder parameter is resolved */) {
        //invokes the DB method which will fetch a todo_list item object by id
        Book book = BookService.getBookById(id);

        //Respond with a 404 if there is no such todo_list item for the id provided
        if(book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a todo_list_item object to return as response
        return Response.ok(book).build();
    }

    //This method represents an endpoint with the URL /todos and a GET request.
    // Since there is no @PathParam mentioned, the /todos as a relative path and a GET request will invoke this method.
    @GET
    @Produces( { MediaType.APPLICATION_JSON })
    public Response getAllTodos() {
        List<Book> bookList = BookService.getAllBooks();
        System.out.println("this is the list of books: " + bookList);

        if(bookList == null || bookList.isEmpty()) {

        }

        return Response.ok(bookList).build();
    }
}

