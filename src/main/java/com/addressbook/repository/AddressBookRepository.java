package com.addressbook.repository;

import java.util.ArrayList;
import java.util.*;

import org.springframework.stereotype.Repository;
import com.addressbook.model.Contact;

@Repository
public class AddressBookRepository {

	private Map<String, List<Contact>> addressBooks = new HashMap<>();

    public void createAddressBook(String name) {
        addressBooks.put(name, new ArrayList<>());
    }

    public void addContact(String bookName, Contact contact) {
        List<Contact> contacts = addressBooks.get(bookName);

        if (contacts != null) {
            contacts.add(contact);
        }
    }

    public List<Contact> getContacts(String bookName) {
        return addressBooks.get(bookName);
    }
}
