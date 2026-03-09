import static org.junit.jupiter.api.Assertions.*;import com.addressbook.model.Contact;
import com.addressbook.service.AddressBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.addressbook.service.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import com.addressbook.service.*;
import com.addressbook.model.*;

import com.addressbook.*;
import com.addressbook.repository.*;
public class AddressBookServiceTest {

	 @Test
	 public void givenContactsInDB_whenRetrieved_shouldReturnList() {

		 AddressBookService service = new AddressBookService();
		 
		 List<Contact> contacts = service.getAllContactsFromDB();

		 assertNotNull(contacts);
	 }
	 
	 AddressBookService addressBookService = new AddressBookService();   // ✅ FIX
	 @Test
	 void givenNewCity_whenUpdated_shouldSyncWithDB() {

	     addressBookService.getAllContactsFromDB();   // load DB data

	     addressBookService.updateContactCity("Bill", "Pune");

	     Contact contactFromDB = addressBookService.getContact("Bill");

	     Contact contactInMemory = addressBookService.getContacts()
	             .stream()
	             .filter(c -> c.getFirstName().equals("Bill"))
	             .findFirst()
	             .orElse(null);

	     assertEquals(contactFromDB, contactInMemory);
	 }
	 
	 @Test
	 void givenDateRange_whenRetrieved_shouldReturnContacts() {

	     List<Contact> contacts = addressBookService
	             .getContactsAddedBetween("2024-01-01", "2024-12-31");

	     assertFalse(contacts.isEmpty());
	 }
	 
	 @Test
	 void givenContacts_whenCountedByCity_shouldReturnProperCount() {

	     Map<String, Integer> cityCount = addressBookService.getContactCountByCity();

	     assertFalse(cityCount.isEmpty());
	 }
	 
	 @Test
	 void givenNewContact_whenAdded_shouldSyncWithDB() {

		 Contact contact = new Contact(
				 "Rahul",
				 "Sharma",
				 "Delhi",
				 "DL",
				 "110001",
				 "9876543210",
				 "rahul@gmail.com"
		);

	 boolean result = addressBookService.addContact(contact);

	 assertTrue(result);
	 }
	 
	 private AddressBookService service;

	    @BeforeEach
	    void setup() {
	        service = new AddressBookService();
	        service.createAddressBook("Family");
	    }

	    @Test
	    void testAddSingleContact() {
	        Contact contact = new Contact("Rahul","Sharma","Delhi","Delhi","110001","9876543210","rahul@gmail.com");
	        service.addContact("Family", contact);

	        List<Contact> contacts = service.getContacts("Family");
	        assertEquals(1, contacts.size());
	        assertEquals("Rahul", contacts.get(0).getFirstName());
	    }

	    @Test
	    void testAddDuplicateContact() {
	        Contact contact1 = new Contact("Aman","Verma","Mumbai","Maharashtra","400001","9876543222","aman@gmail.com");
	        Contact contact2 = new Contact("Aman","Verma","Mumbai","Maharashtra","400001","9876543222","aman@gmail.com");

	        service.addContact("Family", contact1);
	        service.addContact("Family", contact2); // duplicate

	        List<Contact> contacts = service.getContacts("Family");
	        assertEquals(1, contacts.size(), "Duplicate should not be added");
	    }

	    @Test
	    void testSearchByCity() {
	        service.addContact("Family", new Contact("Neha","Gupta","Pune","Maharashtra","411001","9876543333","neha@gmail.com"));
	        service.addContact("Family", new Contact("Rahul","Sharma","Delhi","Delhi","110001","9876543210","rahul@gmail.com"));

	        List<Contact> delhiContacts = service.searchByCity("Delhi");
	        assertEquals(1, delhiContacts.size());
	        assertEquals("Rahul", delhiContacts.get(0).getFirstName());
	    }
	    
	    @Test
	    void testSortByName() {
	        service.addContact("Family", new Contact("Neha","Gupta","Pune","Maharashtra","411001","9876543333","neha@gmail.com"));
	        service.addContact("Family", new Contact("Aman","Verma","Mumbai","Maharashtra","400001","9876543222","aman@gmail.com"));

	        List<Contact> sorted = service.sortByName("Family");
	        assertEquals("Aman", sorted.get(0).getFirstName());
	        assertEquals("Neha", sorted.get(1).getFirstName());
	    }

	    @Test
	    void testGetContactsCount() {
	        service.addContact("Family", new Contact("Rahul","Sharma","Delhi","Delhi","110001","9876543210","rahul@gmail.com"));
	        service.addContact("Family", new Contact("Aman","Verma","Mumbai","Maharashtra","400001","9876543222","aman@gmail.com"));

	        List<Contact> contacts = service.getContacts("Family");
	        assertEquals(2, contacts.size());
	    }
}
