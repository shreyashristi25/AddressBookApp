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

    public void addContact(Contact contact) {
        repository.addContact(contact);
    }

    public List<Contact> getContacts() {
        return repository.getContacts();
    }
}