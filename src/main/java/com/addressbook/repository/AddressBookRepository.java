package com.addressbook.repository;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;
import com.addressbook.model.Contact;
import com.opencsv.CSVWriter;
import com.google.gson.Gson;
import java.sql.Statement;
import java.sql.*;
import java.sql.ResultSet;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
public class AddressBookRepository {

    private Map<String, List<Contact>> addressBooks = new HashMap<>();

    private List<Contact> contacts = new ArrayList<>();
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
    
    public void writeContactsToFile(String bookName, String filePath) {

        List<Contact> contacts = addressBooks.get(bookName);

        try (FileWriter writer = new FileWriter(filePath)) {

            for (Contact contact : contacts) {
                writer.write(contact.toString() + "\n");
            }

            System.out.println("Contacts written to file successfully");

        } catch (IOException e) {
            e.printStackTrace() ;
        }
        }
        
    public void readContactsFromFile(String filePath) {

    	try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    		
    		String line;
    		
    		while ((line = reader.readLine()) != null) {
    			System.out.println(line);
    		}

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    public void writeContactsToCSV(String bookName, String filePath) {

    	List<Contact> contacts = addressBooks.get(bookName);

    	try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {

    		String[] header = {"FirstName", "LastName", "City", "State", "Zip"};
    		writer.writeNext(header);

    		for (Contact contact : contacts) {
    			String[] data = {
    					contact.getFirstName(),
    					contact.getLastName(),
    					contact.getCity(),
    					contact.getState(),
    					contact.getZip()
    			};
    			writer.writeNext(data);
    		}

    		System.out.println("Contacts written to CSV successfully");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	} 
    	
    public void readContactsFromCSV(String filePath) {

    	try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

    		String[] line;

    		while ((line = reader.readNext()) != null) {
    			System.out.println(String.join(", ", line));
    		}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    	    
    public void writeContactsToJSON(String bookName, String filePath) {

    	List<Contact> contacts = addressBooks.get(bookName);
    	
    	Gson gson = new Gson();

    	try (FileWriter writer = new FileWriter(filePath)) {

    		gson.toJson(contacts, writer);

    		System.out.println("Contacts written to JSON file successfully");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    	        
    public void readContactsFromJSON(String filePath) {

    	Gson gson = new Gson();

    	try (FileReader reader = new FileReader(filePath)) {

    		Type contactListType = new TypeToken<List<Contact>>() {}.getType();

    		List<Contact> contacts = gson.fromJson(reader, contactListType);

    		contacts.forEach(System.out::println);

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    	        
    public List<Contact> getAllContactsFromDB() {

        List<Contact> contacts = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/addressbook_service";
        String user = "root";
        String password = "Shreya@2002";

        try {

            Connection con = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM contacts";

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Contact contact = new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone"),
                        rs.getString("email")
                );

                contacts.add(contact);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }
    
    public boolean updateContactCity(String firstName, String city) {

        String sql = "UPDATE contacts SET city=? WHERE first_name=?";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/addressbook_service", "root", "Shreya@2002");
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, city);
            ps.setString(2, firstName);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public Contact getContactFromDB(String firstName) {

        String sql = "SELECT * FROM contacts WHERE first_name=?";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/addressbook_service", "root", "Shreya@2002");
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, firstName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Contact> getContactsByDateRange(String startDate, String endDate) {

        List<Contact> contacts = new ArrayList<>();

        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/addressbook_service",
                    "root",
                    "Shreya@2002");

            String query = "SELECT * FROM contact WHERE date_added BETWEEN ? AND ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, startDate);
            statement.setString(2, endDate);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {

                Contact contact = new Contact();

                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setCity(rs.getString("city"));
                contact.setAddress(rs.getString("address"));

                contacts.add(contact);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }
    
    public Map<String, Integer> getContactCountByCity() {

        Map<String, Integer> cityCountMap = new HashMap<>();

        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/addressbook_service",
                    "root",
                    "Shreya@2002");

            String query = "SELECT city, COUNT(*) as total FROM contact GROUP BY city";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                String city = rs.getString("city");
                int count = rs.getInt("total");

                cityCountMap.put(city, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cityCountMap;
    }
    
    public Map<String, Integer> getContactCountByState() {

        Map<String, Integer> stateCountMap = new HashMap<>();

        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/addressbook_service",
                    "root",
                    "Shreya@2002");

            String query = "SELECT state, COUNT(*) as total FROM contact GROUP BY state";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                String state = rs.getString("state");
                int count = rs.getInt("total");

                stateCountMap.put(state, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stateCountMap;
    }
}