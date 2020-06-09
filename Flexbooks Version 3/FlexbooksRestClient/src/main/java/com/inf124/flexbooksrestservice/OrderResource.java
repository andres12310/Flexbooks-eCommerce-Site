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


import com.inf124.flexbooksrestservice.model.Order;
import com.inf124.flexbooksrestservice.service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orders")
public class OrderResource {
    
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED}) //This method accepts form parameters.
    //If you were to send a POST through a form submit, this method would be called.
    public Response addOrder(@FormParam("firstName") String firstName,
                            @FormParam("lastName") String lastName,
                            @FormParam("email") String email,
                            @FormParam("phoneNo") String phoneNumber,
                            @FormParam("address") String address,   
                            @FormParam("aptNo") String apt,
                            @FormParam("zipCode") String zipCode,
                            @FormParam("shipping") String shippingSpeed,
                            @FormParam("city") String city,
                            @FormParam("state") String state,
                            @FormParam("cardnum") String creditCard,
                            @FormParam("expDate") String cardExpiration,
                            @FormParam("cvv") String cvv){
        Order order = new Order();
        order.setISBN("978-0321982384");
        order.setQuantity("10");
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setEmail(email);
        order.setPhoneNumber(phoneNumber);
        order.setAddress(address);
        order.setApt(apt);
        order.setCity(city);
        order.setState(state);
        order.setZipCode(zipCode);
        order.setShippingSpeed(shippingSpeed);
        order.setCreditCard(creditCard);
        order.setCardExpiration(cardExpiration);
        order.setCVV(cvv);

        System.out.println("These are the attributes of the Order object: " + order.getISBN() + " " + order.getQuantity() + " " + firstName + " " + lastName + " " + email + " " + phoneNumber + " " + address + " " + apt + " " + city + " " + state + " " + zipCode + " " + shippingSpeed + " " + creditCard + " " + cardExpiration + " " + cvv);

        if(OrderService.addOrder(order)) {
            return Response.ok().entity("Order Added Successfully").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
