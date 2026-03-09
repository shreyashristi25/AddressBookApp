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

    private List<Contact> contacts = new ArrayList<>();
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
    
    public List<Contact> sortByName(String addressBookName) {
        return repository.sortByName(addressBookName);
    }

    public List<Contact> sortByCity(String addressBookName) {
        return repository.sortByCity(addressBookName);
    }

    public List<Contact> sortByState(String addressBookName) {
        return repository.sortByState(addressBookName);
    }

    public List<Contact> sortByZip(String addressBookName) {
        return repository.sortByZip(addressBookName);
    }
    
    public void writeContactsToFile(String bookName, String filePath) {
        repository.writeContactsToFile(bookName, filePath);
    }

    public void readContactsFromFile(String filePath) {
        repository.readContactsFromFile(filePath);
    }
    
    public void writeContactsToCSV(String bookName, String filePath) {
        repository.writeContactsToCSV(bookName, filePath);
    }

    public void readContactsFromCSV(String filePath) {
        repository.readContactsFromCSV(filePath);
    }
    
    public void writeContactsToJSON(String bookName, String filePath) {
        repository.writeContactsToJSON(bookName, filePath);
    }

    public void readContactsFromJSON(String filePath) {
        repository.readContactsFromJSON(filePath);
    }
    
    
    public AddressBookService() {
        repository = new AddressBookRepository();   // FIX
    }
    
    public boolean updateContactCity(String firstName, String city) {

        boolean updated = repository.updateContactCity(firstName, city);

        if(updated) {
            contacts = repository.getAllContactsFromDB();   // sync memory with DB
        }

        return updated;
    }
    public AddressBookRepository getRepository() {
        return repository;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    
    public List<Contact> getAllContactsFromDB() {
        contacts = repository.getAllContactsFromDB();
        return contacts;
    }
    public Contact getContact(String firstName) {

        return contacts.stream()
                .filter(contact -> contact.getFirstName().equals(firstName))
                .findFirst()
                .orElse(null);
    }
    
    public List<Contact> getContactsAddedBetween(String startDate, String endDate) {

        return repository.getContactsByDateRange(startDate, endDate);
    }
    
    public Map<String, Integer> getContactCountByCity() {
        return repository.getContactCountByCity();
    }

    public Map<String, Integer> getContactCountByState() {
        return repository.getContactCountByState();
    }
   
}