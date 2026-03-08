package com.addressbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Contact {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;
    
    public Contact() {}
    
    public Contact(String firstName, String lastName, String address, String city, String state, String xip, String phoneNumber, String email) {
    	this.firstName = firstName;
    	this.lastName = lastName; 
    	this.address = address;
    	this.city = city ;
    	this.state = state ;
    	this.zip = zip ;
    	this.phoneNumber = phoneNumber ;
    	this.email = email ;
    }
}