package com.addressbook.service;
import java.util.stream.* ;
import com.addressbook.model.Contact;
import java.util.*;
import io.restassured.specification.ProxySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import com.addressbook.model.Contact;
import com.addressbook.repository.AddressBookRepository;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository repository;

    public AddressBookService() {
        repository = new AddressBookRepository(); // safe for non-Spring tests
    }
    
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
    
    private static final String BASE_URL = "http://localhost:3000"; // JSON server

    // Add a contact safely
    public boolean addContact(Contact contact) {
        if (contact == null) return false;

        try {
            Map<String, Object> contactMap = Map.of(
                    "firstName", contact.getFirstName(),
                    "lastName", contact.getLastName(),
                    "city", contact.getCity(),
                    "state", contact.getState(),
                    "zip", contact.getZip(),
                    "phoneNumber", contact.getPhone(),
                    "email", contact.getEmail()
            );

            RestAssured.given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .body(contactMap)
                    .post("/contacts")
                    .then()
                    .statusCode(201);

            System.out.println("Contact added successfully: " + contact.getFirstName());
            return true;

        } catch (Exception e) {
            System.err.println("Error adding contact: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all contacts from JSON server
    public List<Contact> fetchContactsFromJsonServer() {
        try {
            Contact[] contactsArray = RestAssured.given()
                    .baseUri(BASE_URL)
                    .contentType(ContentType.JSON)
                    .get("/contacts")
                    .as(Contact[].class);

            return Arrays.stream(contactsArray).collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error fetching contacts: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // empty list if error
        }
    }
}