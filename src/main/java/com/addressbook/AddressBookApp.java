package com.addressbook;

import java.util.Scanner;
import com.addressbook.model.Contact;
import com.addressbook.repository.AddressBookRepository;

public class AddressBookApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressBookRepository repository = new AddressBookRepository();

        char choice;

        do {

            Contact contact = new Contact();

            System.out.println("Enter First Name:");
            contact.setFirstName(sc.nextLine());

            System.out.println("Enter Last Name:");
            contact.setLastName(sc.nextLine());

            System.out.println("Enter Address:");
            contact.setAddress(sc.nextLine());

            repository.addContact(contact);

            System.out.println("Do you want to add another contact? (y/n)");
            choice = sc.next().charAt(0);
            sc.nextLine();

        } while (choice == 'y' || choice == 'Y');

    }
}