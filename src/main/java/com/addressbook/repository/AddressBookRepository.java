package com.addressbook.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.addressbook.model.Contact;

@Repository
public class AddressBookRepository {

    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    
    public void editContact(String name, String newAddress) {

        for (Contact contact : contacts) {
            if (contact.getFirstName().equalsIgnoreCase(name)) {
                contact.setAddress(newAddress);
                System.out.println("Address Updated");
            }
        }
    }
}
