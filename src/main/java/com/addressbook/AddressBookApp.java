package com.addressbook;

import java.util.Scanner;
import com.addressbook.model.Contact;
import com.addressbook.repository.AddressBookRepository;

public class AddressBookApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressBookRepository repo = new AddressBookRepository();

        System.out.println("Enter Address Book Name:");
        String bookName = sc.nextLine();

        repo.createAddressBook(bookName);

        System.out.println("Enter First Name:");
        String firstName = sc.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = sc.nextLine();

        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);

        repo.addContact(bookName, contact);

        System.out.println("Contact added to " + bookName);
    }
}