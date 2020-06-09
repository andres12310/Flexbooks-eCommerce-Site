/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inf124.flexbooksrestservice.model;

/**
 *
 * @author asus
 */

public class Order { 
    private String isbn;
    private String quantity;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String apt;
    private String city;
    private String state;
    private String zipCode;
    private String shippingSpeed;
    private String creditCard;
    private String cardExpiration;
    private String cvv;
    
    public String getISBN(){
        return isbn;
    }
    
    public void setISBN(String isbn){
        this.isbn = isbn;
    }
    
    public String getQuantity(){
        return quantity;
    }
    
    public void setQuantity(String quantity){
        this.quantity = quantity;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getApt(){
        return apt;
    }
    
    public void setApt(String apt){
        this.apt = apt;
    }
    
    public String getCity(){
        return city;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    
    public String getState(){
        return state;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
    public String getZipCode(){
        return zipCode;
    }
    
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    
    public String getShippingSpeed(){
        return shippingSpeed;
    }
    
    public void setShippingSpeed(String shippingSpeed){
        this.shippingSpeed = shippingSpeed;
    }
    
    public String getCreditCard(){
        return creditCard;
    }
    
    public void setCreditCard(String creditCard){
        this.creditCard = creditCard;
    }
    
    public String getCardExpiration(){
        return cardExpiration;
    }
    
    public void setCardExpiration(String cardExpiration){
        this.cardExpiration = cardExpiration;
    }
    
    public String getCVV(){
        return cvv;
    }
    
    public void setCVV(String cvv){
        this.cvv = cvv;
    }
}
