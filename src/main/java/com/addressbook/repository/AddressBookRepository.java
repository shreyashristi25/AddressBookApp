package com.addressbook.repository;

import java.util.*;
import java.util.stream.*;
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
    
    public List<Contact> searchByCity(String city) {

        return addressBooks.values().stream()
                .flatMap(List::stream)
                .filter(contact -> contact.getCity().equalsIgnoreCase(city))
                .toList();
    }
    
    public List<Contact> searchByState(String state) {

        return addressBooks.values().stream()
                .flatMap(List::stream)
                .filter(contact -> contact.getState().equalsIgnoreCase(state))
                .toList();
    }
    
    public Map<String, List<Contact>> viewPersonsByCity() {

        return addressBooks.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Contact::getCity));
    }
    
    public Map<String, List<Contact>> viewPersonsByState() {

        return addressBooks.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Contact::getState));
    }
    
    public Map<String, Long> countByCity() {

        return addressBooks.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
    }
    
    public Map<String, Long> countByState() {

        return addressBooks.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
    }
    
    public List<Contact> sortByName(String bookName) {

        List<Contact> contacts = addressBooks.get(bookName);

        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getFirstName))
                .toList();
    }
    
    public List<Contact> sortByCity(String bookName) {

        List<Contact> contacts = addressBooks.get(bookName);

        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getCity))
                .toList();
    }
    
    public List<Contact> sortByState(String bookName) {

        List<Contact> contacts = addressBooks.get(bookName);

        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getState))
                .toList();
    }
    
    public List<Contact> sortByZip(String bookName) {

        List<Contact> contacts = addressBooks.get(bookName);

        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getZip))
                .toList();
    }
}