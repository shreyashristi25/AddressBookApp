import static org.junit.jupiter.api.Assertions.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import com.addressbook.model.Contact;
import com.addressbook.service.AddressBookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class AddressBookServiceTest {

    private AddressBookService service;

    @BeforeEach
    void setup() {
        service = new AddressBookService();  // single service instance
        service.createAddressBook("Family"); // create a test address book
    }

    @Test
    void testAddSingleContact() {
        Contact contact = new Contact("Rahul", "Sharma", "Delhi", "Delhi", "110001", "9876543210", "rahul@gmail.com");
        service.addContact("Family", contact);

        List<Contact> contacts = service.getContacts("Family");
        assertEquals(1, contacts.size());
        assertEquals("Rahul", contacts.get(0).getFirstName());
    }

    @Test
    void testAddDuplicateContact() {
        Contact contact1 = new Contact("Aman", "Verma", "Mumbai", "Maharashtra", "400001", "9876543222", "aman@gmail.com");
        Contact contact2 = new Contact("Aman", "Verma", "Mumbai", "Maharashtra", "400001", "9876543222", "aman@gmail.com");

        service.addContact("Family", contact1);
        service.addContact("Family", contact2); // duplicate

        List<Contact> contacts = service.getContacts("Family");
        assertEquals(1, contacts.size(), "Duplicate should not be added");
    }

    @Test
    void testSearchByCity() {
        service.addContact("Family", new Contact("Neha", "Gupta", "Pune", "Maharashtra", "411001", "9876543333", "neha@gmail.com"));
        service.addContact("Family", new Contact("Rahul", "Sharma", "Delhi", "Delhi", "110001", "9876543210", "rahul@gmail.com"));

        List<Contact> delhiContacts = service.searchByCity("Delhi");
        assertEquals(1, delhiContacts.size());
        assertEquals("Rahul", delhiContacts.get(0).getFirstName());
    }

    @Test
    void testSortByName() {
        service.addContact("Family", new Contact("Neha", "Gupta", "Pune", "Maharashtra", "411001", "9876543333", "neha@gmail.com"));
        service.addContact("Family", new Contact("Aman", "Verma", "Mumbai", "Maharashtra", "400001", "9876543222", "aman@gmail.com"));

        List<Contact> sorted = service.sortByName("Family");
        assertEquals("Aman", sorted.get(0).getFirstName());
        assertEquals("Neha", sorted.get(1).getFirstName());
    }

    @Test
    void testAddContactsFromJsonServer() {
        Contact c1 = new Contact("Alice", "Smith", "Bhopal", "MP", "462001", "1234567890", "alice@example.com");
        Contact c2 = new Contact("Bob", "Sharma", "Indore", "MP", "452001", "2345678901", "bob@example.com");

        service.addContact(c1);
        service.addContact(c2);

        List<Contact> contacts = service.fetchContactsFromJsonServer();
        assertTrue(contacts.size() >= 2);
        System.out.println("Contacts from JSON server:");
        contacts.forEach(c -> System.out.println(c.getFirstName() + " " + c.getLastName()));
    }
}