import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import com.addressbook.service.*;
import com.addressbook.model.*;


public class AddressBookServiceTest {

	 @Test
	    public void givenContactsInDB_whenRetrieved_shouldReturnList() {

	        AddressBookService service = new AddressBookService();

	        List<Contact> contacts = service.getAllContactsFromDB();

	        assertNotNull(contacts);
	    }
}
