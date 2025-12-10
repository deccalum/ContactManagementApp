package se.lexicon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
                    randomizeContacts();
                    break;
                case 6:
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
        IO.println(" 5- Randomize Contacts: ");
        IO.println(" 6- Exit: ");
        IO.println("\n Choose an option: ");
    }

    static void addContact() {
        IO.println("Enter name: ");
        String name = scanner.nextLine();

        IO.println("Enter mobile number: ");
        int mobile = scanner.nextInt();
        scanner.nextLine(); // add invalid check later

        if (mobileExists(mobile)) {
            IO.println("Number already exists.");
            return;
        }

        int id = contact.getContact().size() + 1;
        contact.addContact(new Contact(id, name, mobile));
        IO.println("Contact saved! ");
    }

    static boolean mobileExists(int mobile) {
        ArrayList<Contact> contacts = contact.getContact();
        for (Contact c : contacts) {
            if (c.getMobile() == mobile) {
                return true;
            }
        }
        return false;
    }

    static void getContact() {
        IO.println("Search: ");
        String name = scanner.nextLine().toLowerCase();
        ArrayList<Contact> contacts = contact.getContact();
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
        ArrayList<Contact> contacts = contact.getContact();
        for (Contact c : contacts) {
            IO.println(c.toString());
        }
    }

    static void getContactByMobile() {
        ArrayList<Contact> contacts = contact.getContact();
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

        IO.println("How many random contacts do you want to create?");
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            // Generate random name
            int randomIndex = random.nextInt(names.length);
            String randomName = names[randomIndex];

            // Generate random mobile number (e.g., between 1000000 and 9999999)
            int randomMobile = random.nextInt(9000000) + 1000000;

            // Create new contact and add to list
            int id = contact.getContact().size() + 1;
            contact.addContact(new Contact(id, randomName, randomMobile));

            IO.println("Added random contact: " + randomName + " - " + randomMobile);
        }

        IO.println("Total contacts: " + contact.getContact().size());
    }
}