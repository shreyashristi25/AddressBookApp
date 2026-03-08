package com.addressbook.repository;

import java.util.*;

import com.addressbook.model.Contact;

public class AddressBookRepository {

    private Map<String, List<Contact>> addressBooks = new HashMap<>();

    public void createAddressBook(String name) {
        addressBooks.put(name, new ArrayList<>());
    }

    public void addContact(String bookName, Contact contact) {

        List<Contact> contacts = addressBooks.get(bookName);

        if (contacts != null) {

            boolean isDuplicate = contacts.stream()
                    .anyMatch(c -> c.equals(contact));

            if (isDuplicate) {
                System.out.println("Duplicate contact found");
            } else {
                contacts.add(contact);
                System.out.println("Contact added successfully");
            }
        }
    }

    public List<Contact> getContacts(String addressBookName) {
        return addressBooks.get(addressBookName);
    }
}