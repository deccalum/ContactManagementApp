package se.lexicon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Contact contact = new Contact();

    public static void main(String[] args) {
        boolean isRun = true;
        int option;
        do {
            menu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addContact();
                    break;
                case 2:
                    getContact();
                    break;
                case 3:
                    getContactByMobile();
                    break;
                case 4:
                    listContacts();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 6:
                    createCSV();
                    break;
                case 7:
                    randomizeContacts();
                    break;
                case 8:
                    isRun = false;
                    break;
                default:
                    IO.println("Wrong Input");
            }
        } while (isRun);
    }

    static void menu() {
        IO.println("\n=== Contact Management ===");
        IO.println(" 1- Add Contact: ");
        IO.println(" 2- Search Contact: ");
        IO.println(" 3- Search Contact by Number: ");
        IO.println(" 4- List All Contacts: ");
        IO.println(" 5- Delete Contact: ");
        IO.println(" 6- Export Contacts to CSV: ");
        IO.println(" 7- Randomize Contacts: ");
        IO.println(" 8- Exit: ");
        IO.println("\n Choose an option: ");
    }

    static void addContact() {
        IO.println("Enter name: ");
        String name = scanner.nextLine();

        IO.println("Enter mobile number: ");
        int mobile;
        if (!scanner.hasNextInt()) {
            IO.println("Invalid mobile number format. Not a number.");
            scanner.nextLine();
            return;
        }
        mobile = scanner.nextInt();
        scanner.nextLine();
        contact.addContact(name, mobile);
    }

    static void getContact() {
        IO.println("Search: ");
        String name = scanner.nextLine().toLowerCase();
        ArrayList<Contact> contacts = contact.getContactList();
        boolean found = false;

        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(name)) {
                IO.println(c.toString());
                found = true;
            }
        }
        if (!found) {
            IO.println("Contact not found.");
        }
    }

    static void listContacts() {
        ArrayList<Contact> contacts = contact.getContactList();
        for (Contact c : contacts) {
            IO.println(c.toString());
        }
    }

    static void deleteContact() {
        IO.println("Delete by [1] ID or [2] Mobile number?");
        int input = scanner.nextInt();
        if (input != 1 && input != 2) {
            IO.println("Invalid option.");
            scanner.nextLine();
            return;
        }
        
        int contactIDorMobile = scanner.nextInt();
        ArrayList<Contact> contacts = contact.getContactList();
        if (input == 1) {
            IO.println("Enter contact ID to delete: ");
            for (int i = 0; i < contacts.size(); i++) {
                IO.println("Enter contact ID to delete: ");
                Contact c = contacts.get(i);
                if (c.getId() == contactIDorMobile) {
                    contacts.remove(i);
                    IO.println("Contact deleted: " + c);
                    return;
                }
            }
            IO.println("Contact not found.");
            return;
        } else {
            IO.println("Enter mobile number to delete: ");
            for (int i = 0; i < contacts.size(); i++) {
                Contact c = contacts.get(i);
                if (c.getMobile() == contactIDorMobile) {
                    contacts.remove(i);
                    IO.println("Contact deleted: " + c);
                    return;
                }
            }
            IO.println("Contact not found.");
        }
    }

    static void getContactByMobile() {
        ArrayList<Contact> contacts = contact.getContactList();
        IO.println("Enter mobile number: ");
        int number = scanner.nextInt();
        scanner.nextLine();
        for (Contact person : contacts) {
            if (person.getMobile() == number) {
                IO.println("\n" + person);
                return;
            }
        }
    }

    static void randomizeContacts() {

        String[] names = { "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah", "Ian", "Jack" };
        Random random = new Random();

        IO.println("How many times to random contacts?");
        if (!scanner.hasNextInt()) {
            IO.println("Invalid input. Not a number.");
            scanner.nextLine();
            return;
        }
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            // generate random name from namearray
            int randomIndex = random.nextInt(names.length);
            String randomName = names[randomIndex];

            // generate random mobile number
            int randomMobile = random.nextInt(9000000) + 1000000;

            // create new contact from random data
            int id = contact.getContactList().size() + 1;
            contact.addContact(randomName, randomMobile);
        }

        IO.println("Total contacts: " + contact.getContactList().size());
    }

    static void createCSV() {
        // name and path
        String fileName = "Contacts.csv";
        ArrayList<Contact> contacts = contact.getContactList();

        /*
         * try-with-resources: File operations throw IOException. Java requires you to
         * explicitly handle these exceptions (either with a try-catch block or by
         * throwing the exception further up).
         */

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID,Name,Mobile");
            writer.newLine();
            for (Contact c : contacts) {
                writer.write(c.getId() + "," + c.getName() + "," + c.getMobile() + "\n");
            }
            IO.println("\nContacts exported to " + fileName + "\n");
        } catch (IOException e) {
            IO.println("Error writing to file: " + e.getMessage() + "\n");
        }
    }
}

/*
 * TODOS
 * add string input aceptance for various phone number formats
 * read from file to populate contact list on startup
 * add edit contact functionality
 * add delete contact functionality
 * add validation for name input (no numbers or special characters)
 */