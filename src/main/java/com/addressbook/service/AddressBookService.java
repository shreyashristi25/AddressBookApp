package com.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.model.Contact;
import com.addressbook.repository.AddressBookRepository;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository repository;

    public void createAddressBook(String name) {
        repository.createAddressBook(name);
    }

    public void addContact(String addressBookName, Contact contact) {
        repository.addContact(addressBookName, contact);
    }

    public List<Contact> getContacts(String addressBookName) {
        return repository.getContacts(addressBookName);
    }
}