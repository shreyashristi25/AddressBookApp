package com.addressbook.service;

import java.util.*;

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
    
    public List<Contact> searchByCity(String city) {
        return repository.searchByCity(city);
    }

    public List<Contact> searchByState(String state) {
        return repository.searchByState(state);
    }
    
    public Map<String, List<Contact>> viewPersonsByCity() {
        return repository.viewPersonsByCity();
    }

    public Map<String, List<Contact>> viewPersonsByState() {
        return repository.viewPersonsByState();
    }
    
    public Map<String, Long> countByCity() {
        return repository.countByCity();
    }

    public Map<String, Long> countByState() {
        return repository.countByState();
    }

}