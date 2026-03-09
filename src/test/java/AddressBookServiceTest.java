import static org.junit.jupiter.api.Assertions.*;

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
}
