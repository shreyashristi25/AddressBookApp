package com.addressbook;

import java.util.List;
import java.util.Scanner;

import com.addressbook.model.Contact;
import com.addressbook.service.AddressBookService;

public class AddressBookApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressBookService service = new AddressBookService();

        System.out.println("Enter AddressBook Name:");
        String bookName = sc.nextLine();

        List<Contact> sortedContacts = service.sortByName(bookName);

        System.out.println("Sorted Contacts:");

        sortedContacts.forEach(System.out::println);
        
        System.out.println("Enter AddressBook name:");
        String book = sc.nextLine();

        String filePath = "contacts.txt";

        service.writeContactsToFile(book, filePath);

        System.out.println("Reading contacts from file:");

        service.readContactsFromFile(filePath);

        sc.close();
    }
}